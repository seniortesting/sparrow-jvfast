/**
 * 判断一个元素是否在数组中
 */
export const contains = (arr, val) => {
  return !!arr.includes(val)
}

/**
 * @param  {arr} 数组
 * @param  {fn} 回调函数
 * @return {undefined}
 */
export const each = (arr, fn) => {
  fn = fn || Function
  const a = []
  const args = Array.prototype.slice.call(arguments, 1)
  for (let i = 0; i < arr.length; i++) {
    const res = fn.apply(arr, [arr[i], i].concat(args))
    if (res != null) {
      a.push(res)
    }
  }
}

/**
 * @param  {arr} 数组
 * @param  {fn} 回调函数
 * @param  {thisObj} this指向
 * @return {Array}
 */
export const map = (arr, fn, thisObj) => {
  const scope = thisObj || window
  const a = []
  for (let i = 0, j = arr.length; i < j; ++i) {
    const res = fn.call(scope, arr[i], i, this)
    if (res != null) {
      a.push(res)
    }
  }
  return a
}

/**
 * @param  {arr} 数组
 * @param  {type} 1：从小到大   2：从大到小   3：随机
 * @return {Array}
 */
export const sort = (arr, type = 1) => {
  return arr.sort((a, b) => {
    switch (type) {
      case 1:
        return a - b
      case 2:
        return b - a
      case 3:
        return Math.random() - 0.5
      default:
        return arr
    }
  })
}
/**
 * 去重
 */
export const unique = (arr) => {
  // eslint-disable-next-line no-prototype-builtins
  if (Array.hasOwnProperty('from')) {
    return Array.from(new Set(arr))
  } else {
    const n = {}
    const r = []
    for (let i = 0; i < arr.length; i++) {
      if (!n[arr[i]]) {
        n[arr[i]] = true
        r.push(arr[i])
      }
    }
    return r
  }
}

/**
 * 求两个集合的并集
 */
export const union = (a, b) => {
  const newArr = a.concat(b)
  return this.unique(newArr)
}

/**
 * 求两个集合的交集
 */
export const intersect = (a, b) => {
  const _this = this
  a = this.unique(a)
  return this.map(a, function (o) {
    return _this.contains(b, o) ? o : null
  })
}

/**
 * 删除其中一个元素
 */
export const remove = (arr, ele) => {
  const index = arr.indexOf(ele)
  if (index > -1) {
    arr.splice(index, 1)
  }
  return arr
}

/**
 * 将类数组转换为数组的方法
 */
export const formArray = (ary) => {
  let arr = []
  if (Array.isArray(ary)) {
    arr = ary
  } else {
    arr = Array.prototype.slice.call(ary)
  }

  return arr
}

/**
 * 最大值
 */
export const max = (arr) => {
  return Math.max.apply(null, arr)
}

/**
 * 最小值
 */
export const min = (arr) => {
  return Math.min.apply(null, arr)
}

/**
 * 求和
 */
export const sum = (arr) => {
  return arr.reduce((pre, cur) => {
    return pre + cur
  })
}

/**
 * 平均值
 */
export const average = (arr) => {
  return this.sum(arr) / arr.length
}
/**
 * 判断a数组是否包含b数组中
 */
export const getArrRepeat = (arr1, arr2) => {
  return arr1.filter((item, index) => {
    return arr2.includes(item)
  })
}
/**
 * 将数组分片
 * 列子[1,2,3,4,5,6,7,8] [[1,2,3],[4,5,6],[7,8]]
 */
export const arrChunk = (data = [], space = 5) => {
  const result = []
  for (let i = 0, len = data.length; i < len; i += space) {
    result.push(data.slice(i, i + space))
  }
  return { data: result, total: data.length, space }
}
