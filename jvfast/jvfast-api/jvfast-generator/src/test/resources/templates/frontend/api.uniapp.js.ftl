import http from '@/plugin/http'
import keys from '@/config/keys'

const ${cfg.functionName?upper_case}_ADD_URL = '/${package.ModuleName}/${cfg.functionName}/add'
const ${cfg.functionName?upper_case}_UPDATE_URL = '/${package.ModuleName}/${cfg.functionName}/update'
const ${cfg.functionName?upper_case}_REMOVE_URL = '/${package.ModuleName}/${cfg.functionName}/del'
const ${cfg.functionName?upper_case}_REMOVE_BATCH_URL = '/${package.ModuleName}/${cfg.functionName}/delbatch'
const ${cfg.functionName?upper_case}_ID_URL = '/${package.ModuleName}/${cfg.functionName}/id'
const ${cfg.functionName?upper_case}_LIST_URL = '/${package.ModuleName}/${cfg.functionName}/list'
const ${cfg.functionName?upper_case}_PAGE_URL = '/${package.ModuleName}/${cfg.functionName}/page'

const ${cfg.functionName} = {
    add${entity}: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_ADD_URL, data).then(res => {
                const resCode = res.data.code
                resolve(resCode)
            }).catch(err => {
                reject(err)
            })
        })
    },
    update${entity}: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_UPDATE_URL, data).then(res => {
                const resCode = res.data.code
                resolve(resCode)
            }).catch(err => {
                reject(err)
            })
        })
    },
    remove${entity}: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_REMOVE_URL, data).then(res => {
                const resCode = res.data.code
                resolve(resCode)
            }).catch(err => {
                reject(err)
            })
        })
    },
    remove${entity}s: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_REMOVE_BATCH_URL, data).then(res => {
                const resCode = res.data.code
                resolve(resCode)
            }).catch(err => {
                reject(err)
            })
        })
    },
    get${entity}ById: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_ID_URL, data).then(res => {
                const resData = res.data
                resolve(resData)
            }).catch(err => {
                reject(err)
            })
        })
    },
    list${entity}: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_LIST_URL, data).then(res => {
                const resData = res.data
                resolve(resData)
            }).catch(err => {
                reject(err)
            })
        })
    },
    page${entity}: (data) => {
        return new Promise((resolve, reject) => {
            http.post(${cfg.functionName?upper_case}_PAGE_URL, data).then(res => {
                const resData = res.data
                resolve(resData)
            }).catch(err => {
                reject(err)
            })
        })
    }
}

export default ${cfg.functionName}
