import { createStore } from 'vuex'

export default createStore({
  state: {
    username: '',
    isLogin: false,
    beforePath: '/index'
  },
  mutations: {
    login(state,username){
      state.username = username;
      state.isLogin = true;
    },
    logout(state){
      state.username = '';
      state.isLogin = false;
    },
    toLogin(state,route){
      state.beforePath = route;
    },
    fromLogin(state){
      state.beforePath = '/index';
    }
  },
  actions: {
    login(context,username){
      context.commit('login',username);
    },
    logout(context){
      context.commit('logout');
    },
    toLogin(context,route){
      context.commit('toLogin',route);
    },
    fromLogin(context){
      context.commit('fromLogin')
    }
  },
  modules: {
  }
})
