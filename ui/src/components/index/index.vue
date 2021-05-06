<template>
    <div>index</div>
    <template v-if="!isLogin">
        <div><router-link @click="toLogin" to="#">登录</router-link></div>
        <div><router-link to="/register">注册</router-link></div>
    </template>
    <template v-else>
        <div>{{username}}</div>
    </template>
    <div><a href="#" @click="logout">登出</a></div>
</template>

<script>
    import axios from '@/global/axiosConfig.js';
    import {mapState} from "vuex";
    export default {
        name: "index",
        methods:{
            toLogin(){
                this.$router.push("/login");
            },logout(){
                axios.post('logout')
                .then(data=>{
                    console.log(data);
                },error => {
                    console.log(error);
                });
                this.$store.dispatch('logout');
            }
            },computed:{
            ...mapState({
                isLogin: state=>state.user.isLogin,
                username:  state=>state.user.username
            })
        }
    }
</script>

<style scoped>

</style>