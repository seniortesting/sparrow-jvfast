const db = require('../model/db')

/**
 * 相关执行SQL语句
 */
const sql = {
  addOrderSql: `
   INSERT INTO web_order (id, create_by, create_time, update_by, update_time)
  VALUES (?, ?, NOW(), ?,NOW());
  `
}
const sample = {
  /**
   * 预定订单
   * @param req
   * @param res
   */
  addSample: (req, res) => {
    const result = db.result()
    try {
      const sampleInfo = req.body
      console.log('请求参数是: ', sampleInfo)
      // 数据库端操作，插入数据
      db.exec(sql.addOrderSql, [db.nextId(), sampleInfo.firstname, sampleInfo.userId], (error, results, fields) => {
        console.log(error, results, fields)
        if (error) {
          result.code = 500
          result.message = JSON.stringify(error)
          res.json(result)
        } else {
          res.json(result)
        }
      })
    } catch (err) {
      result.code = 500
      result.message = JSON.stringify(err)
      res.json(result)
    }
  }
}

module.exports = sample
