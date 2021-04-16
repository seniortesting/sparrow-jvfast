import upload from '@/api/sys/upload'

export const state = () => ({
})
export const mutations = {}
export const actions = {
  deleteFile ({ commit }, data) {
    return new Promise((resolve, reject) => {
      upload.delFile(data).then((res) => {
        resolve(res)
      }).catch((err) => {
        reject(err)
      })
    })
  }
}
