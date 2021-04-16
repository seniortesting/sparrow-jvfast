/**
 * 查询树节点
 * @param treeData
 * @param id
 * @param path
 * @return {*[]|undefined}
 */
export const findTreeNode = function (treeData, id, path = []) {
  for (let i = 0; i < treeData.length; i++) {
    const tmpPath = path.concat()
    const node = treeData[i]
    if (node.id === id) {
      tmpPath.push(node)
      return tmpPath
    }
    if (node.children) {
      const nodeResult = findTreeNode(node.children, id, tmpPath)
      if (nodeResult) {
        return nodeResult
      }
    }
  }
}

/**
 * 树形数据转换
 * @param {*} data
 * @param {*} id
 * @param {*} pid
 */
export const translateTree = function (data, id = 'id', pid = 'pid') {
  const res = []
  const temp = {}
  for (let i = 0; i < data.length; i++) {
    temp[data[i][id]] = data[i]
  }
  for (let k = 0; k < data.length; k++) {
    if (temp[data[k][pid]] && data[k][id] !== data[k][pid]) {
      if (!temp[data[k][pid]].children) {
        temp[data[k][pid]].children = []
      }
      if (!temp[data[k][pid]]._level) {
        temp[data[k][pid]]._level = 1
      }
      data[k]._level = temp[data[k][pid]]._level + 1
      temp[data[k][pid]].children.push(data[k])
    } else {
      res.push(data[k])
    }
  }
  return res
}
