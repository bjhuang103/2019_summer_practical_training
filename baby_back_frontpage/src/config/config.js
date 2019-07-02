let mode = 'prod'
let baseUrl = ''
if (mode === 'dev') {
  baseUrl = 'http://localhost:18080'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else if (mode == 'prod') {
  baseUrl = 'http://42.159.4.66:18080'
}

// 填写信息部分
const lostBabyInsertUrl = baseUrl + "/lost-baby/insert"
const lostBabyFindUrl = baseUrl + "/lost-baby/find"
const matchBabyInsertUrl = baseUrl + "/match-baby/insert"
const matchBabyFindUrl = baseUrl + "/match-baby/find"
const uploadPictureUrl = baseUrl + "/file/upload"

// 登录注册部分
const loginUrl = baseUrl + "/login"
const registUrl = baseUrl + "/register"
const sendSMSUrl = baseUrl + "/sms-verify"

// 论坛部分
const articleFindUrl = baseUrl + '/article/find'
const articleDeleteUrl = baseUrl + '/article/delete'
const articleFindByUserUrl = baseUrl + '/article/find-by-user'
const articleInsertUrl = baseUrl + '/article/insert'

//查询用户信息
const userFindUrl = baseUrl + '/user/find'

const URLS = {
  baseUrl,
  lostBabyInsertUrl,
  uploadPictureUrl,
  loginUrl,
  registUrl,
  matchBabyInsertUrl,
  lostBabyFindUrl,
  matchBabyFindUrl,
  sendSMSUrl,
  articleFindUrl,
  articleDeleteUrl,
  articleFindByUserUrl,
  articleInsertUrl,
  userFindUrl
}

module.exports = URLS
