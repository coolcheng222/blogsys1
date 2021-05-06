<template>
    <router-view></router-view>
</template>

<script>

    import axios from '@/global/axiosConfig.js';
    import {mapState} from "vuex";

    export default {
        name: 'App',
        components: {},
        mounted() {
            // check login
            axios({
                url:'/login',
                method:'POST'
            }).then(data=>{
                if(data.data.errno === 0){
                    this.$store.dispatch('login',data.data.extend);
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
                beforePath:'beforePath'
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
</style>
