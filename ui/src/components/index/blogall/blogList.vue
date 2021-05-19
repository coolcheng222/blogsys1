<template>
    <ul class="infinite-list" v-infinite-scroll="load" style="overflow:auto">
        <template v-if="loading">

            <!--eslint-disable-next-line-->
            <li class="infinit-list-item" v-for="i in [1,2,3,4,5]">
                    <el-skeleton :rows="2" animated="true"/>
            </li>

        </template>
        <template v-else>
            <!--eslint-disable-next-line-->
            <li class="infinit-list-item" v-for="item in list">
                <blog-li :item="item"></blog-li>
            </li>
            <p class="prompt" v-if="page.hasNextPage">加载中...</p>
            <p class="prompt" v-else>没有更多了</p>

        </template>
    </ul>
</template>

<script>
    import axios from '@/global/axiosConfig.js';
    import {Page} from "../../../global/clazz";
    import BlogLi from "./blogLi";
    export default {
        name: "blogList",
        components: {BlogLi},
        data() {
            return {
                loading: true,
                page: new Page(),
                list: [],
                time: undefined
            }
        },
        mounted() {
            // this.load();
            this.time = new Date().getTime();
        },
        methods: {
            getPage(n){
                axios({
                    url: `/post`,
                    params:{
                        before: this.time,
                        page: n
                    }
                }).then(data=>{
                    console.log(data);
                    if(data.data.errno == 1){
                        return Promise.reject(data.data.message)
                    }else{
                        return data.data;
                    }
                },error=>{
                    return Promise.reject(error.toString());
                }).then(data=>{
                    this.loading = false;
                    this.list = [...this.list,...data.extend.list];
                    Object.assign(this.page,data.extend);

                },msg=>{
                    console.log(msg);
                })
            },
            load() {
                if(!this.page.isLastPage){
                    this.getPage(this.page.nextPage);
                }
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
        height: 110px;
        padding: 10px;
        border-bottom: 1px solid #E7E7E7;
        background-color: #fff;
    }


    .el-footer span{
        font-size: 16px;
        color: #99aaaa;
    }

    .prompt{
        height: 50px;
        text-align: center;
        margin-bottom: 10px;
        line-height: 50px;
    }
</style>