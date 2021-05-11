import { createStore } from 'vuex'
import qs from "qs";
import axios from '@/global/axiosConfig.js';


let userModule = {
  state:{
    username: '',
    uid: '',
    isLogin: false
  },
  mutations: {
    login(state,user){
      state.username = user.username;
      state.isLogin = true;
      state.uid = user.uid;
    },
    logout(state){
      state.username = '';
      state.uid = '';
      state.isLogin = false;
    }
  },
  actions: {
    login(context,userInfo){
      let {username,password,kaptcha} = userInfo;
      return axios({
        url: 'login',
        method: "POST",
        data: qs.stringify({
          username, password, kaptcha
        }),
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        }
      })
          .then(data => {
        if (data.data.errno === 1) {
          return Promise.reject(data.data.message);
        } else {
          context.commit('login',data.data.extend);
          return data.data.extend;
        }
      },
      msg => {
        console.log(msg);
        return Promise.reject(msg.toString());
      });
    },
    logout(context){
      context.commit('logout');
    }
  }
}
let loginPageModule = {
  state: {
    beforePath: '/index'
  },
  mutations:{
    toLogin(state,route){
      state.beforePath = route;
    },
    fromLogin(state){
      state.beforePath = '/index';
    }
  },
  actions:{
    toLogin(context,route){
      // console.log(route);
      context.commit('toLogin',route);
    },
    fromLogin(context){
      context.commit('fromLogin')
    }
  }
}
export default createStore({
  modules: {
    user: userModule,
    loginPage: loginPageModule
  }
})
