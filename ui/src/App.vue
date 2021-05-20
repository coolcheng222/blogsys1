<template>
    <router-view></router-view>
</template>

<script>

    import axios from '@/global/axiosConfig.js';
    import {mapState} from "vuex";

    export default {
        name: 'App',
        components: {},
        created() {
            // check login
            axios({
                url:'/login',
                method:'POST'
            }).then(data=>{
                if(data.data.errno === 0){
                    this.$store.dispatch('login',data.data.extend);
                    // this.$router.go(0);
                    let b = /login|register/.test(this.$route.fullPath);
                    if(b){
                        this.$router.push(this.$store.state.loginPage.beforePath);
                    }
                }
            });
            // $router

        },
        methods: {
            send(){
            }
        },
        computed:{
            ...mapState({
                beforePath: state=>state.loginPage.beforePath,
                isLogin: state=>state.user.isLogin
            })
        }
    }
</script>

<style>
    *{
        margin: 0;
        padding: 0;
    }
    html,body,#app {
        height: 100%;
        /*text-align: center;*/
        /*color: #2c3e50;*/
        /*margin-top: 60px;*/
    }
    body{
        background-color: #EAEAEA;
    }
    .userpop{
        padding-left: 0 !important;
        padding-right: 0 !important;
        background-color: #f3dbdb !important;
    }
</style>
