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
                            <div class="title">{{item.username}}</div>
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
    export default {
        name: "Subing",
        data(){
            return {
                lists:[],
                loading:[true,true],
                title:['关注','粉丝']
            }
        },
        mounted() {
            this.getSubbed();
            this.getSubing();
        },
        methods:{
            getSubbed(){
                this.loading[0] = true;
                axios.get(`/sub/subing/${this.$route.params.uid}`)
                .then(
                    data=>{
                        if(data.data.errno === 0){
                            this.lists[0] = data.data.extend.userInfos;
                            this.loading[0] = false;
                        }
                    }
                )
            },
            getSubing(){
                this.loading[1] = true;
                axios.get(`/sub/fans/${this.$route.params.uid}`)
                    .then(
                        data=>{
                            if(data.data.errno === 0){
                                this.lists[1] = data.data.extend.userInfos;
                                this.loading[1] = false;
                            }
                        }
                    )
            }
        }
    }
</script>

<style scoped>
    .ohh{
        list-style: none;
        width: 80%;
        height: 30px;
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