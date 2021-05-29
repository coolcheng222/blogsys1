<template>
    <template v-if="loading">
        <i class="el-icon-loading"></i>
    </template>
    <template v-else-if="list.length === 0">
        <div class="reply">
            无
        </div>
    </template>
    <template v-else>
        <!--eslint-disable-->
        <div class="reply" v-for="item in list">
            <span class="content">{{item.content}}</span><br>
            <span class="title" @click="$router.push(`/post/${item.post.pId}`)">文章: {{item.post.title}}</span>
        </div>
    </template>
</template>

<script>
    import {Page} from "../../../global/clazz";
    import axios from 'axios';
    export default {
        name: "reply",
        data(){
            return {
                list:[],
                page:new Page(),
                loading: true
            }
        },
        mounted() {
            this.getReply(1);
            },
        methods:{
            getReply(page){
                this.loading = true;
                axios.get(`/reply/user/${this.$route.params.uid}?page=${page}`)
                .then(
                    data=>{
                        if(data.data.errno === 0){
                            this.list = data.data.extend.list;
                            this.page = data.data.extend;
                            this.loading = false;
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>
    .reply{
        width: 80%;
        height: 60px;
        background-color: #fff;
        border-radius: 20px;
        display: -webkit-box;
        -webkit-line-clamp: 3;
        -webkit-box-orient: vertical;
        word-break: break-all;
        padding-left: 30px;
        margin-top: 10px;
    }
    .content{
        height: 20px;
        width: 80%;
        display: block;
    }
    .title{
        margin-top: 12px;
        font-size: 14px;
        color: #eeadad;
        cursor: pointer;
    }
    .title:hover{
        text-decoration: underline;
    }
</style>