<template>
    <ul  class="oh" v-if="!loading">
        <!--eslint-disable-->
        <li class="ohh" @click="$router.push(`/post/${item.pId}`)" v-for="item in list">
            <div class="title">{{item.title}}</div>
            <span class="author" @click="$router.push(`/user/${item.author.uid}`)">{{item.author.username}}</span><br>
            <span class="time">{{getTime(item.time)}}</span>
        </li>
    </ul>
    <i class="el-icon-loading" v-else></i>
</template>

<script>
    import axios from 'axios'
    import {dateFormat} from "../../../global/clazz";

    export default {
        name: "Subscribe",
        data() {
            return {
                list: [],
                loading: true,
                before:0
            }
        },
        mounted() {
            this.before = new Date().getTime();
            this.getTopic(1);
        },
        methods:{
            getTopic(page){
                this.loading = true;
                axios.get(`/topic/${this.$route.params.uid}`,{
                    params:{
                        page,
                        before: this.before
                    }
                }).then(
                    data=>{
                        if(data.data.errno === 0){
                            this.list = data.data.extend;
                            this.loading = false;
                        }
                    }
                )
            },
            getTime(timestamp){
                return dateFormat(`YYYY-mm-dd HH:MM:SS`,timestamp)
            }
        }
    }
</script>

<style scoped>
    .ohh{
        list-style: none;
        width: 80%;
        height: 60px;
        border-radius: 10px;
        margin-top: 10px;
        background-color: rgba(255,255,255,1);
        cursor: pointer;
        padding-top: 10px;
    }
    .title{
        margin-left: 14px;
        color: #9e8f8f;
        font-size: 19px;
        cursor: pointer;
        margin-bottom: 2px;

    }
    .author{
        margin-left: 15px;
    }
    .author:hover{
        color: #cb0eff;
    }
    .ohh:hover{
        background-color: #f5c0c0;   
    }
    .time{
        margin-left: 15px;
        font-size: 14px;
        color: #9bb694;
        margin-bottom: 10px;

    }
</style>