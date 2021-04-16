const express = require('express')
const router = express.Router()
// const bodyParser = require('body-parser')
const { body } = require('express-validator')
// ------------------接口设置列表
// 文件上海穿
const upload = require('../model/multer')
const file = require('./file')
router.post('/file/upload', upload.any(), file.upload)
router.post('/file/del', [
  body('url').notEmpty().withMessage('file url should not empty'),
  body('token').notEmpty().withMessage('file token should not empty')
], file.delete)
// ------------------ 用户登录注册，忘记密码接口
const user = require('./user')
router.post('/user/login', [
  body('username').notEmpty().withMessage('username should not empty'),
  body('passwd').notEmpty().withMessage('password should not empty')
], user.login)
// ...其他相关接口
const sample = require('./sample')
router.post('/sample/add', sample.addSample)

module.exports = router
