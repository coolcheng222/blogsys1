
import axios from 'axios'

console.log(axios);
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
export default axios;
