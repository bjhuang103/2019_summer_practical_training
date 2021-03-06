import http from './interface'
import store from '@/store'

/**
 * 将业务所有接口统一起来便于维护
 * 如果项目很大可以将 url 独立成文件，接口分成不同的模块
 * 
 */

// 单独导出(测试接口) import {test} from '@/common/vmeitime-http/'
export const test = (data) => {
	/* http.config.baseUrl = "http://localhost:8080/api/" */
	//设置请求前拦截器
	http.interceptor.request = (config) => {
		console.log("请求拦截器：",store.state.token)
		if(store.state.token != '') {
			config.header.Authorization = store.state.token
		}
	} 
	//设置请求结束后拦截器
	http.interceptor.response = (response) => {
		console.log('个性化response....')
		//判断返回状态 执行相应操作
		return response;
	}
    return http.request({
		baseUrl: 'https://unidemo.dcloud.net.cn/',
        url: 'ajax/echo/text?name=uni-app',
		dataType: 'text',
        data,
    })
}

// 轮播图
export const banner = (data) => {
    return http.request({
        url: '/banner/36kr',
        method: 'GET', 
        data,
		// handle:true
    })
}

// get方法
export const get = (url,params) => {
    return http.get(url,params)
}

// post方法
export const post = (url,data) => {
    return http.post(url,data)
}
export const post2 = (url,data,headers) => {
	let options = {}
	options.url = url
	options.data = data
	options.header = headers
	return http.request(options)
}

// put方法
export const put = (url,data) => {
    return http.put(url,data)
}

export const request = (config) => {
	return http.request(config)
}

export const _delete = (url,data) => {
	return http.delete(url,data)
}

// 默认全部导出  import api from '@/common/vmeitime-http/'
export default {
	test,
    banner,
    get,
    post,
    put,
	request,
	post2,
	_delete
}