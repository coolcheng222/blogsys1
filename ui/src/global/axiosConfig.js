
import axios from 'axios'
// eslint-disable-next-line no-unused-vars
import router from "../router";

// console.log(axios);
// var protocol = "http";
// var host = "localhost";
// var port = "8080";
// var contextPath = "blogserver";
var path  = "/api/";
if(window.location.port == 8080){
    // console.log("aaa");
    path = "/blogserver/";
}
axios.defaults.crossDomain=true;
axios.defaults.baseURL = path;
axios.defaults.withCredentials = true;
axios.interceptors.response.use(
    data=>{
        // console.log("拦截拦截")
        // console.log(data);
        if(data.data.errno == 4) {
            router.push("/login");
            return Promise.reject("需要登录");
        }
        return data;
    }
)
export default axios;
