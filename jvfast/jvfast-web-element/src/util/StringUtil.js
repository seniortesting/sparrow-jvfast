/**
 * 去除空格
 * @param  {str}
 * @param  {type}
 * type:  1-所有空格  2-前后空格  3-前空格 4-后空格
 * @return {String}
 */
export const trim = (str, type) => {
  type = type || 1
  switch (type) {
    case 1:
      return str.replace(/\s+/g, '')
    case 2:
      return str.replace(/(^\s*)|(\s*$)/g, '')
    case 3:
      return str.replace(/(^\s*)/g, '')
    case 4:
      return str.replace(/(\s*$)/g, '')
    default:
      return str
  }
}

/**
 * @param  {str}
 * @param  {type}
 *       type:  1:首字母大写  2：首字母小写  3：大小写转换  4：全部大写  5：全部小写
 * @return {String}
 */
export const changeCase = (str, type) => {
  type = type || 4
  switch (type) {
    case 1:
      return str.replace(/\b\w+\b/g, function (word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase()
      })
    case 2:
      return str.replace(/\b\w+\b/g, function (word) {
        return word.substring(0, 1).toLowerCase() + word.substring(1).toUpperCase()
      })
    case 3:
      return str.split('').map(function (word) {
        if (/[a-z]/.test(word)) {
          return word.toUpperCase()
        } else {
          return word.toLowerCase()
        }
      }).join('')
    case 4:
      return str.toUpperCase()
    case 5:
      return str.toLowerCase()
    default:
      return str
  }
}

/*
*  检测密码强度
*/
export const checkPwd = (str) => {
  let Lv = 0
  if (str.length < 6) {
    return Lv
  }
  if (/[0-9]/.test(str)) {
    Lv++
  }
  if (/[a-z]/.test(str)) {
    Lv++
  }
  if (/[A-Z]/.test(str)) {
    Lv++
  }
  if (/[\.|-|_]/.test(str)) {
    Lv++
  }
  return Lv
}

/**
 * 函数节流器
 * @param  {Function} fn 需要执行性的函数
 * @param  {number} time 时间戳
 * @param  {number} interval 间隔时间
 */
export const debouncer = (fn, time, interval = 200) => {
  if (time - (window.debounceTimestamp || 0) > interval) {
    fn && fn()
    window.debounceTimestamp = time
  }
}

/**
 * 在字符串中插入新字符串
 * @param {string} soure 源字符
 * @param {string} index 插入字符的位置
 * @param {string} newStr 需要插入的字符
 * @returns {string} 返回新生成的字符
 */
export const insertStr = (soure, index, newStr) => {
  const str = soure.slice(0, index) + newStr + soure.slice(index)
  return str
}

/**
 * 判断两个对象是否键值相同
 * @param  {Object}  a 第一个对象
 * @param  {Object}  b 第一个对象
 * @return {Boolean}   相同返回true，否则返回false
 */
export const isObjectEqual = (a, b) => {
  const aProps = Object.getOwnPropertyNames(a)
  const bProps = Object.getOwnPropertyNames(b)

  if (aProps.length !== bProps.length) {
    return false
  }

  for (let i = 0; i < aProps.length; i++) {
    const propName = aProps[i]

    if (a[propName] !== b[propName]) {
      return false
    }
  }
  return true
}

/**
 * 16进制颜色转RGB\RGBA字符串
 * @param  {String} val 16进制颜色值
 * @param  {Number} opa 不透明度，取值0~1
 * @return {String}     转换后的RGB或RGBA颜色值
 */
export const colorToRGB = (val, opa) => {
  const pattern = /^(#?)[a-fA-F0-9]{6}$/ // 16进制颜色值校验规则
  const isOpa = typeof opa === 'number' // 判断是否有设置不透明度

  if (!pattern.test(val)) { // 如果值不符合规则返回空字符
    return ''
  }

  const v = val.replace(/#/, '') // 如果有#号先去除#号
  const rgbArr = []
  let rgbStr = ''

  for (let i = 0; i < 3; i++) {
    const item = v.substring(i * 2, i * 2 + 2)
    const num = parseInt(item, 16)
    rgbArr.push(num)
  }

  rgbStr = rgbArr.join()
  rgbStr = 'rgb' + (isOpa ? 'a' : '') + '(' + rgbStr + (isOpa ? ',' + opa : '') + ')'
  return rgbStr
}

/**
 * 追加url参数
 * @param {string} url url参数
 * @param {string|object} key 名字或者对象
 * @param {string} value 值
 * @return {string} 返回新的url
 * @example
 * appendQuery('lechebang.com', 'id', 3);
 * appendQuery('lechebang.com?key=value', { cityId: 2, cityName: '北京'});
 */
export const appendQuery = (url, key, value) => {
  let options = key
  if (typeof options === 'string') {
    options = {}
    options[key] = value
  }
  // eslint-disable-next-line no-undef
  options = $.param(options)
  if (url.includes('?')) {
    url += '&' + options
  } else {
    url += '?' + options
  }
  return url
}
