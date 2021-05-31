<template>
    <el-row>
        <el-col :span="1"></el-col>
        <!--eslint-disable-->
        <template v-for="(list,index) in lists">
            <template v-if="loading[index]">
                <i class="el-icon-loading"></i>
            </template>
            <template v-else>
                <el-col :span="1"><div class="title">{{title[index]}}</div></el-col>
                <el-col :span="10">
                    <ul >
                        <li class="ohh" @click="$router.push(`/user/${item.uid}`)" v-for="item in list">
                            <div class="title">{{item.mtitle}}</div>
                            <div class="title">{{item.content}}</div>
                            <div class="title">{{getTime(item.time)}}</div>
                        </li>
                    </ul>
                </el-col>
            </template>

        </template>
        <el-col :span="1"></el-col>
    </el-row>
</template>

<script>
    import axios from 'axios'
    import {dateFormat} from "../global/clazz";

    export default {
        name: "message2",
        data(){
            return {
                lists:[],
                loading:[true,true],
                title:['未读消息','其他消息'],
                time: new Date().getTime()
            }
        },
        mounted() {
            this.getUnread();
            this.getmessages();
        },
        methods:{
            getUnread(){
                this.loading[0] = true;
                axios.get(`/unread/${this.uid}`)
                    .then(
                        data=>{
                            if(data.data.errno === 0){
                                this.lists[0] = data.data.extend;
                                this.loading[0] = false;
                            }
                        }
                    )
            },
            getmessages(){
                this.loading[1] = true;
                axios.get(`/msg/${this.uid}?before=${this.time}`)
                    .then(
                        data=>{
                            if(data.data.errno === 0){
                                this.lists[1] = data.data.extend.list;
                                this.loading[1] = false;
                            }
                        }
                    )
            },
            getTime(time){
                return dateFormat("YYYY-mm-dd HH:MM:SS",time);
            }
        },
        computed:{
            uid(){
                return this.$store.state.user.uid;
            }
        }
    }
</script>

<style scoped>
    .ohh{
        list-style: none;
        width: 80%;
        height: 70px;
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