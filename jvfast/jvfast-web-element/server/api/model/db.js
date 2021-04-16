const mysql = require('mysql')

// 雪花算法计算id
const Snowflake = require('better-snowflake')
const config = require('../config')
const idWorker = new Snowflake(config.snowflake.worker_id, config.snowflake.datacenter_id)
// 建立连接池，并设置连接池
const pool = mysql.createPool(config.db)
pool.on('acquire', function (connection) {
  console.log('Connection %d acquired', connection.threadId)
})
pool.on('enqueue', function () {
  console.log('Waiting for available connection slot')
})
pool.on('release', function (connection) {
  console.log('Connection %d released', connection.threadId)
})

/**
 * select,update,delete,insert操作
 * @param query
 * @param bindParameters
 * @return 返回结果
 *     得到返回id: results.insertId
 *     得到insert, update or delete的数据条数: results.affectedRows
 *     得到update更改的条数，如果数据没有更改则不会计数： results.changedRows
 *     得到count(1)对应的数据条数: results[0].count
 */
const exec = function (query = '', bindParameters = [], callback) {
  pool.getConnection((err, connection) => {
    if (err) {
      console.log('CONNECT Thread ', connection.threadId, ', ERROR:', err.message)
    } else {
      if ((!bindParameters) || bindParameters.length === 0) {
        bindParameters = []
      }
      console.log(`[SQL] Query is: ${query}, parameters are: ${bindParameters.toString()}`)
      connection.query(query, bindParameters, (error, results, fields) => {
        // When done with the connection, release it.
        connection.release()
        if (error) {
          callback(error, null, fields)
        } else {
          callback(null, results, fields)
        }
      })
    }
  })
}
/**
 * 返回的通用json结果
 * @return {{code: number, data: {}, message: string, timestamp: string}}
 */
const result = function () {
  const result = require('./result')
  return result
}
/**
 * 获取一个采用雪花算法的id值
 * @return {SnowflakeId}
 */
const nextId = function () {
  return idWorker.nextId()
}

module.exports = { exec, nextId, result }
