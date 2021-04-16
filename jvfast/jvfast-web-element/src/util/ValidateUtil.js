/**
 * validate email
 * @param email
 * @returns {boolean}
 */
import { deepClone } from './ObjectUtil'

export function isEmail (email) {
  // 参考另外一个正则： /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/
  const re = /^(([^<>()\\[\]\\.,;:\s@"]+(\.[^<>()\\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
  return re.test(email)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}

/* 合法uri */
export function isValidURL (textval) {
  const urlregex = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/
  return urlregex.test(textval)
}

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/* 小写字母 */
export function isLowerCase (str) {
  const reg = /^[a-z]+$/
  return reg.test(str)
}

/* 大写字母 */
export function isUpperCase (str) {
  const reg = /^[A-Z]+$/
  return reg.test(str)
}

/* 大小写字母 */
export function isAlphabets (str) {
  const reg = /^[A-Za-z]+$/
  return reg.test(str)
}

export function isPermissionCode (str) {
  const reg = /^[A-Za-z:]+$/
  return reg.test(str)
}

/**
 * 判断身份证号码
 */
export function isIDCard (code) {
  const list = []
  let result = true
  let msg = ''
  const city = {
    11: '北京',
    12: '天津',
    13: '河北',
    14: '山西',
    15: '内蒙古',
    21: '辽宁',
    22: '吉林',
    23: '黑龙江 ',
    31: '上海',
    32: '江苏',
    33: '浙江',
    34: '安徽',
    35: '福建',
    36: '江西',
    37: '山东',
    41: '河南',
    42: '湖北 ',
    43: '湖南',
    44: '广东',
    45: '广西',
    46: '海南',
    50: '重庆',
    51: '四川',
    52: '贵州',
    53: '云南',
    54: '西藏 ',
    61: '陕西',
    62: '甘肃',
    63: '青海',
    64: '宁夏',
    65: '新疆',
    71: '台湾',
    81: '香港',
    82: '澳门',
    91: '国外 '
  }
  if (!isNull(code)) {
    if (code.length === 18) {
      if (!code || !/(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(code)) {
        msg = '证件号码格式错误'
      } else if (!city[code.substr(0, 2)]) {
        msg = '地址编码错误'
      } else {
        // 18位身份证需要验证最后一位校验位
        code = code.split('')
        // ∑(ai×Wi)(mod 11)
        // 加权因子
        const factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
        // 校验位
        const parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2, 'x']
        let sum = 0
        let ai = 0
        let wi = 0
        for (let i = 0; i < 17; i++) {
          ai = code[i]
          wi = factor[i]
          sum += ai * wi
        }
        if (parity[sum % 11] !== code[17]) {
          msg = '证件号码校验位错误'
        } else {
          result = false
        }
      }
    } else {
      msg = '证件号码长度不为18位'
    }
  } else {
    msg = '证件号码不能为空'
  }
  list.push(result)
  list.push(msg)
  return list
}

/**
 * 严格的身份证校验
 * @param {*} sId
 */
export const isCardID = (sId) => {
  if (!/(^\d{15}$)|(^\d{17}(\d|X|x)$)/.test(sId)) {
    console.log('你输入的身份证长度或格式错误')
    return false
  }
  // 身份证城市
  const aCity = {
    11: '北京',
    12: '天津',
    13: '河北',
    14: '山西',
    15: '内蒙古',
    21: '辽宁',
    22: '吉林',
    23: '黑龙江',
    31: '上海',
    32: '江苏',
    33: '浙江',
    34: '安徽',
    35: '福建',
    36: '江西',
    37: '山东',
    41: '河南',
    42: '湖北',
    43: '湖南',
    44: '广东',
    45: '广西',
    46: '海南',
    50: '重庆',
    51: '四川',
    52: '贵州',
    53: '云南',
    54: '西藏',
    61: '陕西',
    62: '甘肃',
    63: '青海',
    64: '宁夏',
    65: '新疆',
    71: '台湾',
    81: '香港',
    82: '澳门',
    91: '国外'
  }
  if (!aCity[parseInt(sId.substr(0, 2))]) {
    console.log('你的身份证地区非法')
    return false
  }

  // 出生日期验证
  const sBirthday = (sId.substr(6, 4) + '-' + Number(sId.substr(10, 2)) + '-' + Number(sId.substr(12, 2))).replace(/-/g, '/')
  const d = new Date(sBirthday)
  if (sBirthday !== (d.getFullYear() + '/' + (d.getMonth() + 1) + '/' + d.getDate())) {
    console.log('身份证上的出生日期非法')
    return false
  }

  // 身份证号码校验
  let sum = 0
  const weights = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
  const codes = '10X98765432'
  for (let i = 0; i < sId.length - 1; i++) {
    sum += sId[i] * weights[i]
  }
  const last = codes[sum % 11] // 计算出来的最后一位身份证号码
  if (sId[sId.length - 1] !== last) {
    console.log('你输入的身份证号非法')
    return false
  }

  return true
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * 判断手机号码是否正确
 */
export function isValidPhone (phone) {
  const list = []
  let result = true
  let msg = ''
  const phoneReg = /^0\d{2,3}-?\d{7,8}$/
  // 增加134 减少|1349[0-9]{7}，增加181,增加145，增加17[678]
  if (!isNull(phone)) {
    if (phone.length === 11) {
      if (phoneReg.test(phone)) {
        msg = '手机号码格式不正确'
      } else {
        result = false
      }
    } else {
      msg = '手机号码长度不为11位'
    }
  } else {
    msg = '手机号码不能为空'
  }
  list.push(result)
  list.push(msg)
  return list
}

/**
 * 判断姓名是否正确
 */
export function isName (name) {
  const regName = /^[\u4E00-\u9FA5]{2,4}$/
  if (!regName.test(name)) {
    return false
  }
  return true
}

/**
 * 是否字符串
 */
export const isString = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'String'
}

/**
 * 判断是否为整数
 */
export function isNumber (num, type) {
  let regName = /[^\d.]/g
  if (type === 1) {
    if (!regName.test(num)) {
      return false
    }
  } else if (type === 2) {
    regName = /[^\d]/g
    if (!regName.test(num)) {
      return false
    }
  }
  return true
}

/**
 * 判断是否为小数
 */
export function isFloatNumber (num, type) {
  let regName = /[^\d.]/g
  if (type === 1) {
    if (!regName.test(num)) {
      return false
    }
  } else if (type === 2) {
    regName = /[^\d.]/g
    if (!regName.test(num)) {
      return false
    }
  }
  return true
}

/**
 * 是否boolean
 */
export const isBoolean = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Boolean'
}
/**
 * 是否函数
 */
export const isFunction = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Function'
}

/**
 * 判断是否为空
 */
export function isNull (val) {
  // 特殊判断
  if (val && parseInt(val) === 0) { return false }
  const list = ['$parent']
  if (typeof val === 'boolean') {
    return false
  }
  if (typeof val === 'number') {
    return false
  }
  if (Array.isArray(val)) {
    if (val.length === 0) { return true }
  } else if (val instanceof Object) {
    val = deepClone(val)
    list.forEach((ele) => {
      delete val[ele]
    })
    if (JSON.stringify(val) === '{}') { return true }
  } else {
    if (
      val === 'null' ||
      val == null ||
      val === 'undefined' ||
      val === undefined ||
      val === ''
    ) {
      return true
    }
    return false
  }
  return false
}

/**
 * 是否undefined
 */
export const isUndefined = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Undefined'
}

/**
 * 是否对象
 */
export const isObj = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Object'
}
/**
 * /是否数组
 */
export const isArray = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Array'
}

/**
 * 是否时间
 */
export const isDate = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Date'
}

/**
 * 是否正则
 */
export const isRegExp = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'RegExp'
}

/**
 * 是否错误对象
 */
export const isError = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Error'
}

/**
 * 是否Symbol函数
 */
export const isSymbol = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Symbol'
}

/**
 * 是否Promise对象
 */
export const isPromise = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Promise'
}

/**
 * 是否Set对象
 */
export const isSet = (o) => {
  return Object.prototype.toString.call(o).slice(8, -1) === 'Set'
}

/**
 * 是否是微信浏览器
 */
export const isWeiXin = () => {
  const ua = navigator.userAgent.toLowerCase()
  return ua.match(/microMessenger/i) === 'micromessenger'
}

/**
 * 是否是移动端
 */
export const isDeviceMobile = () => {
  const ua = navigator.userAgent.toLowerCase()
  return /android|webos|iphone|ipod|balckberry/i.test(ua)
}
/**
 * 是否是QQ浏览器
 */
export const isQQBrowser = () => {
  const ua = navigator.userAgent.toLowerCase()
  return !!ua.match(/mqqbrowser|qzone|qqbrowser|qbwebviewtype/i)
}
/**
 * 是否是爬虫
 */
export const isSpider = () => {
  const ua = navigator.userAgent.toLowerCase()
  return /adsbot|googlebot|bingbot|msnbot|yandexbot|baidubot|robot|careerbot|seznambot|bot|baiduspider|jikespider|symantecspider|scannerlwebcrawler|crawler|360spider|sosospider|sogou web sprider|sogou orion spider/.test(ua)
}
/**
 * 是否ios
 */
export const isIos = () => {
  const u = navigator.userAgent
  if (u.includes('Android') || u.includes('Linux')) { // 安卓手机
    return false
  } else if (u.includes('iPhone')) { // 苹果手机
    return true
  } else if (u.includes('iPad')) { // iPad
    return false
  } else if (u.includes('Windows Phone')) { // winphone手机
    return false
  } else {
    return false
  }
}
/**
 * 是否为PC端
 */
export const isPC = () => {
  const userAgentInfo = navigator.userAgent
  const Agents = ['Android', 'iPhone',
    'SymbianOS', 'Windows Phone',
    'iPad', 'iPod']
  let flag = true
  for (let v = 0; v < Agents.length; v++) {
    if (userAgentInfo.indexOf(Agents[v]) > 0) {
      flag = false
      break
    }
  }
  return flag
}
/**
 * 密码
 * @param value
 * @return {boolean}
 */
export const isPassword = function (value) {
  // 密码为8~20位数字和字母组合
  return /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,20}$/.test(value)
}
/**
 * 判断类型集合
 * @param {*} str
 * @param {*} type
 */
export const checkStr = (str, type) => {
  switch (type) {
    case 'phone': // 手机号码
      return /^1[3|4|5|6|7|8|9][0-9]{9}$/.test(str)
    case 'tel': // 座机
      return /^(0\d{2,3}-\d{7,8})(-\d{1,4})?$/.test(str)
    case 'card': // 身份证
      return /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(str)
    case 'pwd': // 密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线
      return /^[a-zA-Z]\w{5,17}$/.test(str)
    case 'postal': // 邮政编码
      return /[1-9]\d{5}(?!\d)/.test(str)
    case 'QQ': // QQ号
      return /^[1-9][0-9]{4,9}$/.test(str)
    case 'email': // 邮箱
      return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(str)
    case 'money': // 金额(小数点2位)
      return /^\d*(?:\.\d{0,2})?$/.test(str)
    case 'URL': // 网址
      return /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/.test(str)
    case 'IP': // IP
      return /((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))/.test(str)
    case 'date': // 日期时间
      return /^(\d{4})\-(\d{2})\-(\d{2}) (\d{2})(?:\:\d{2}|:(\d{2}):(\d{2}))$/.test(str) || /^(\d{4})\-(\d{2})\-(\d{2})$/.test(str)
    case 'number': // 数字
      return /^[0-9]$/.test(str)
    case 'english': // 英文
      return /^[a-zA-Z]+$/.test(str)
    case 'chinese': // 中文
      return /^[\\u4E00-\\u9FA5]+$/.test(str)
    case 'lower': // 小写
      return /^[a-z]+$/.test(str)
    case 'upper': // 大写
      return /^[A-Z]+$/.test(str)
    case 'HTML': // HTML标记
      return /<("[^"]*"|'[^']*'|[^'">])*>/.test(str)
    default:
      return true
  }
}

/**
 *判断是否为json对象
 */

export const isJson = (str) => {
  if (Array.isArray(str)) {
    if (str[0] instanceof Object) {
      return true
    } else {
      return false
    }
  } else if (str instanceof Object) {
    return true
  }
  return false
}
