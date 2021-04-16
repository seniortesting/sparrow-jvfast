/**
 * 通用返回结果
 * @type {{code: number, data: {}, success: boolean, message: string, timestamp: string}}
 */
const Result = new function () {
  this.timestamp = new Date().toISOString()
  this.code = 0
  this.success = this.code === 0
  this.message = (this.code === 0 && '成功') || '失败'
  this.data = {}
}()

module.exports = Result
