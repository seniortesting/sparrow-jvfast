
const { validationResult } = require('express-validator')
const CryptoJS = require('crypto-js')
const db = require('../model/db')
const config = require('../../../src/config')

const sql = {
  loginSql: `
   SELECT su.id,su.user,su.pass,su.email,su.status,su.update_time FROM sys_user su WHERE su.user=?
  `
}
const user = {
  login: (req, res, next) => {
    const result = db.result()
    const errors = validationResult(req)
    if (!errors.isEmpty()) {
      result.code = 422
      result.data = errors.array()
      return res.json(result)
    }
    try {
      const user = req.body.username
      const pass = req.body.passwd
      const params = [user]
      db.exec(sql.loginSql, params, (error, results, fields) => {
        if (error) {
          result.code = 501
          result.message = JSON.stringify(error)
          return res.json(result)
        }
        if (results.length === 0) {
          result.code = 400
          result.message = 'Not found username'
          return res.json(result)
        }
        const userInfo = results[0]
        // 进行解密操作
        const encryptPasswd = userInfo.pass
        const secretKey = config.secretKey
        const md5Pass = CryptoJS.AES.decrypt(encryptPasswd, secretKey).toString(CryptoJS.enc.Utf8)
        if (md5Pass !== pass) {
          result.code = 300
          result.message = 'Incorrect password'
          return res.json(result)
        }
        // 返回结果
        delete userInfo.pass
        result.code = 0
        result.data = userInfo
        res.json(result)
      })
    } catch (err) {
      result.code = 500
      result.message = JSON.stringify(err)
      res.json(result)
    }
  }

}
module.exports = user
