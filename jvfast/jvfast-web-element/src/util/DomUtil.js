/**
 * 去除html标签
 * @param {*} str
 */
export const removeHtmltag = (str) => {
  return str.replace(/<[^>]+>/g, '')
}

/**
 * 获取url参数
 * @param {*} name
 */
export const getQueryString = (name) => {
  const reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i')
  const search = window.location.search.split('?')[1] || ''
  const r = search.match(reg) || []
  return r[2]
}

/**
 * 动态引入js
 * @param {*} src
 */
export const injectScript = (src) => {
  const s = document.createElement('script')
  s.type = 'text/javascript'
  s.async = true
  s.src = src
  const t = document.getElementsByTagName('script')[0]
  t.parentNode.insertBefore(s, t)
}
/**
 * 根据url地址下载
 * @param {*} url
 */
export const download = (url) => {
  const isChrome = navigator.userAgent.toLowerCase().includes('chrome')
  const isSafari = navigator.userAgent.toLowerCase().includes('safari')
  if (isChrome || isSafari) {
    const link = document.createElement('a')
    link.href = url
    if (link.download !== undefined) {
      const fileName = url.substring(url.lastIndexOf('/') + 1, url.length)
      link.download = fileName
    }
    if (document.createEvent) {
      const e = document.createEvent('MouseEvents')
      e.initEvent('click', true, true)
      link.dispatchEvent(e)
      return true
    }
  }
  if (!url.includes('?')) {
    url += '?download'
  }
  window.open(url, '_self')
  return true
}
/**
 * el是否包含某个class
 * @param {*} el
 * @param {*} className
 */
export const hasClass = (el, className) => {
  return !!el.className.match(new RegExp('(\\s|^)' + className + '(\\s|$)'))
}
/**
 * el添加某个class
 * @param {*} el
 * @param {*} className
 */
export const addClass = (el, className) => {
  if (hasClass(el, className)) {
    return
  }
  const newClass = el.className.split(' ')
  newClass.push(className)
  el.className = newClass.join(' ')
}

/**
 * el去除某个class
 * @param {*} el
 * @param {*} className
 */
export const removeClass = (el, className) => {
  if (!hasClass(el, className)) {
    return
  }
  const reg = new RegExp('(^|\\s)' + className + '(\\s|$)', 'g')
  el.className = el.className.replace(reg, ' ')
}
/**
 * 获取滚动的坐标
 * @param {*} el
 */
export const getScrollPosition = (el = window) => ({
  x: el.pageXOffset !== undefined ? el.pageXOffset : el.scrollLeft,
  y: el.pageYOffset !== undefined ? el.pageYOffset : el.scrollTop
})

/**
 * 滚动到顶部
 */
export const scrollToTop = () => {
  const c = document.documentElement.scrollTop || document.body.scrollTop
  if (c > 0) {
    window.requestAnimationFrame(scrollToTop)
    window.scrollTo(0, c - c / 8)
  }
}

/**
 * el是否在视口范围内
 * @param {*} el
 * @param {*} partiallyVisible
 */
export const elementIsVisibleInViewport = (el, partiallyVisible = false) => {
  const { top, left, bottom, right } = el.getBoundingClientRect()
  const { innerHeight, innerWidth } = window
  return partiallyVisible
    ? ((top > 0 && top < innerHeight) || (bottom > 0 && bottom < innerHeight)) &&
    ((left > 0 && left < innerWidth) || (right > 0 && right < innerWidth))
    : top >= 0 && left >= 0 && bottom <= innerHeight && right <= innerWidth
}

/**
 * 洗牌算法随机
 * @param {*} arr
 */
export const shuffle = (arr) => {
  const result = []
  let random
  while (arr.length > 0) {
    random = Math.floor(Math.random() * arr.length)
    result.push(arr[random])
    arr.splice(random, 1)
  }
  return result
}

/**
 * 劫持粘贴板
 * @param {*} value
 */
export const copyTextToClipboard = (value) => {
  const textArea = document.createElement('textarea')
  textArea.style.background = 'transparent'
  textArea.value = value
  document.body.appendChild(textArea)
  textArea.select()
  try {
    // eslint-disable-next-line no-unused-vars
    const successful = document.execCommand('copy')
  } catch (err) {
    console.log('Oops, unable to copy')
  }
  document.body.removeChild(textArea)
}

/**
 * 动态加载css文件
 * @param {string} url
 */
export const injectStyle = (url) => {
  const link = document.createElement('link')
  link.type = 'text/css'
  link.rel = 'stylesheet'
  link.href = url
  const head = document.getElementsByTagName('head')[0]
  head.appendChild(link)
}
/**
 * 打开小窗口
 */
export const openWindow = (url, title, w, h) => {
  // Fixes dual-screen position                            Most browsers       Firefox
  const dualScreenLeft = window.screenLeft !== undefined ? window.screenLeft : screen.left
  const dualScreenTop = window.screenTop !== undefined ? window.screenTop : screen.top

  const width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width
  const height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height

  const left = ((width / 2) - (w / 2)) + dualScreenLeft
  const top = ((height / 2) - (h / 2)) + dualScreenTop
  const newWindow = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=yes, copyhistory=no, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left)

  // Puts focus on the newWindow
  if (window.focus) {
    newWindow.focus()
  }
}

/**
 * 触发 window.resize
 */
export function triggerWindowResizeEvent () {
  const event = document.createEvent('HTMLEvents')
  event.initEvent('resize', true, true)
  event.eventType = 'message'
  window.dispatchEvent(event)
}

/**
 * 原始的http请求
 * @param options
 */
export const ajax = function (options) {
  options = options || {}
  const xhr = new XMLHttpRequest()
  if (options.type === 'buffer') {
    xhr.responseType = 'arraybuffer'
  }
  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4) {
      const status = xhr.status
      if (status >= 200 && status < 300) {
        options.success && options.success(xhr.response)
      } else {
        options.fail && options.fail(status)
      }
    }
  }
  xhr.open('GET', options.url, true)
  xhr.send(null)
}
