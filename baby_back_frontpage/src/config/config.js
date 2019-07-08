let mode = 'dev'
let baseUrl = ''
let baseUrl2 = ''
if (mode === 'dev') {
  baseUrl = 'http://127.0.0.1:18080'
  baseUrl2 = 'http://127.0.0.1:18080'
} else if (mode == 'hbj'){
  baseUrl = 'http://192.168.137.1:18080'
} else if (mode == 'prod'){
  baseUrl = 'http://42.159.4.66:9999/api/main'
  baseUrl2 = 'http://42.159.4.66:9999'
} else if (mode == 'test') {
  baseUrl = 'http://127.0.0.1:9999/api/main'
  baseUrl2 = 'http://127.0.0.1:9999'
} 

// 填写信息部分
const lostBabyInsertUrl = baseUrl + "/lost-baby/insert"
const lostBabyFindUrl = baseUrl + "/lost-baby/find"
const lostBabyFindByUserUrl = baseUrl + "/lost-baby/find-by-user"
const matchBabyInsertUrl = baseUrl + "/match-baby/insert"
const matchBabyFindUrl = baseUrl + "/match-baby/find"
const matchBabyFindByUserUrl = baseUrl + "/match-baby/find-by-user"
const uploadPictureUrl = baseUrl + "/file/upload"
const uploadTxtAndRecogUrl = baseUrl + "/file/analyze-txt"

// 登录注册部分
// const loginUrl = baseUrl2 + "/api/auth/oauth/token"
const loginUrl = baseUrl + "/login"
const registUrl = baseUrl + "/register"
const sendSMSUrl = baseUrl + "/sms-verify"
// const userInfoUrl = baseUrl2 + '/api/admin/user/infoTel'
const userInfoUrl = baseUrl + '/user/infoTel'
// const logoutUrl = baseUrl2+ '/api/auth/token/logout'
const logoutUrl = baseUrl + '/logout'

const userUpdateUrl = baseUrl + '/user/update'

// 论坛部分
const articleFindUrl = baseUrl + '/article/find'
const articleDeleteUrl = baseUrl + '/article/delete'
const articleFindByUserUrl = baseUrl + '/article/find-by-user'
const articleInsertUrl = baseUrl + '/article/insert'
const articleLike = baseUrl + '/article/like'
const articleInfiniteUrl = baseUrl + '/article/infinite-scroll'
const commentFindByArticle = baseUrl + '/comment/find-by-article'
const commentInsertUrl = baseUrl + '/comment/insert'
const commentInfiniteUrl = baseUrl + "/comment/infinite-scroll"
const articleFindByUserNameUrl = baseUrl + '/article/find-by-username'
const userFindUrl = baseUrl + '/user/find'
const userFindByIdUrl = baseUrl + '/user/find-by-id'

// 消息收发
const websocketUrl = baseUrl + "/websocket"


const URLS = {
  baseUrl,
  lostBabyInsertUrl,
  uploadPictureUrl,
  uploadTxtAndRecogUrl,
  loginUrl,
  registUrl,
  matchBabyInsertUrl,
  lostBabyFindUrl,
  lostBabyFindByUserUrl,
  matchBabyFindUrl,
  matchBabyFindByUserUrl,
  sendSMSUrl,
  articleFindUrl,
  articleDeleteUrl,
  articleFindByUserUrl,
  articleInsertUrl,
  articleLike,
  articleInfiniteUrl,
  userFindUrl,
  commentFindByArticle,
  commentInsertUrl,
  commentFindByArticle,
  commentInfiniteUrl,
  userFindByIdUrl,
  articleFindByUserNameUrl,
  userInfoUrl,
  logoutUrl,
  websocketUrl,
  userUpdateUrl
}

export default URLS

