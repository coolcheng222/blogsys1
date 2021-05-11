<template>
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
        <template v-if="loading">

            <!--eslint-disable-next-line-->
            <li class="infinit-list-item" v-for="i in [1,2,3,4]">
                    <el-skeleton :rows="2" animated="true"/>
            </li>
        </template>
        <template v-else>

            <!--eslint-disable-next-line-->
            <li class="infinit-list-item" v-for="item in list">
                <el-container>
                    <el-aside width="40px">Aside</el-aside>
                    <el-container>
                        <el-container>
                            <el-header height="10px"><h3>{{item.title}}</h3></el-header>
                            <el-main>{{item.description}}</el-main>
                            <el-footer height="5px">
                                <span class="author">{{item.author.username}}</span>
                                <span class="time">{{getTime(item.time)}}</span>
                                <span class="replays" style="color: red">TODO</span>
                            </el-footer>
                        </el-container>
                    </el-container>
                </el-container>
            </li>
        </template>
    </ul>
</template>

<script>
    import axios from '@/global/axiosConfig.js';
    import {dateFormat, Page} from "../../global/clazz";
    export default {
        name: "blogList",
        data() {
            return {
                loading: true,
                page: new Page(),
                list: []
            }
        },
        mounted() {
            // this.load();
        },
        methods: {
            getPage(n){
                axios({
                    url: `/post?page=${n}`,
                }).then(data=>{
                    if(data.data.errno == 1){
                        return Promise.reject(data.data.message)
                    }else{
                        return data.data;
                    }
                },error=>{
                    return Promise.reject(error.toString());
                }).then(data=>{
                    this.loading = false;
                    this.list = data.extend.list;
                    Object.assign(this.page,data.extend.page);

                },msg=>{
                    alert(msg);
                })
            },
            load() {
                if(this.page.isLastPage === null){
                    this.getPage(1);
                }else{
                    this.getPage(1);
                }
            },
            getTime(date){
                return dateFormat("YYYY-mm-dd HH:MM",date);
            }
        },
    }

</script>

<style scoped>
    .infinite-list{
        margin-left: 21px;
        margin-right: 21px;
    }
    .infinite-list  .infinit-list-item{
        height: 100px;
        padding: 10px;
        border-bottom: 1px solid #E7E7E7;
        background-color: #fff;
    }
    .el-header >>> h3:hover{
        text-decoration: underline;
        cursor: pointer;
    }
    .time{
        margin-left: 10px;
    }
    .el-main{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        word-break: break-all;
        height: 58px;
    }
    .el-footer span{
        font-size: 16px;
        color: #99aaaa;
    }
    .author:hover{
        text-decoration: underline;
        cursor: pointer;
    }
    .el-main:hover{
        cursor: pointer;
    }
</style>