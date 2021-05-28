<template>
    <el-row>
        <el-col :span="16">
            <div>
<!--                eslint-disable-->
                <template v-if="!loading">
                    <blog-li3 :item="item" v-for="item in list"></blog-li3>
                    <div class="block">
                        <el-pagination

                                layout="prev, pager, next"
                                :page-count="page.pages"
                                :current-page="page.pageNum"
                                @current-change="getPage">
                        </el-pagination>
                    </div>
                </template>
                <template v-else>
<i class="el-icon-loading"></i>
                </template>


            </div>
        </el-col>
        <el-col :span="8">

            <div class="uid">
                <i class="el-icon-s-custom"></i>
                uid: {{uid}}
            </div>
        </el-col>
    </el-row>
</template>

<script>
    import axios from 'axios'
    import BlogLi3 from "../blogLi3";
    import {Page} from "../../../global/clazz";
    export default {
        name: "MyArticle",
        components: {BlogLi3},
        props:['uid'],
        data(){
            return {
                loading: true,
                list:[],
                page: new Page()
            }
        },
        mounted() {
            // eslint-disable-next-line no-empty
                this.getPage(1);

        },
        methods:{
            getPage(page){
                this.loading = true;
                console.log(page);
                axios.get(`/post/user/${this.$route.params.uid}?page=${page}`).then(
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
    .uid{
        margin-top: 20px;
        margin-left: 30px;
        border: 1px black solid;
        border-radius: 3px;
        height: 20px;
        background-color: #fff;
    }
    .block{
        display: flex;
        align-items: center;
        margin-top: 20px;
        margin-left: 200px;
    }
</style>