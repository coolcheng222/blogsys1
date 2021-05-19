import { createStore } from 'vuex'
import qs from "qs";
import axios from '@/global/axiosConfig.js';
import {Post} from "../global/clazz";


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
      axios.post('logout')
          .then(data => {
            console.log(data);
          }, error => {
            console.log(error);
          });
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
let draft = {
  state:{
    post: new Post(),
    hasDraft: false
  },
  mutations:{
    save(state,post) {
      state.post = post;
      state.hasDraft = true;
    },
    clear(state){
      console.log("aaaaacaaaaa");
      state.post = new Post();
      state.hasDraft = false;
    }
  },actions:{
    save(context,post){
      context.commit('save',post);
    },clear(context){
      context.commit('clear');
    }
  }
}
export default createStore({
  modules: {
    user: userModule,
    loginPage: loginPageModule,
    draft
  }
})
