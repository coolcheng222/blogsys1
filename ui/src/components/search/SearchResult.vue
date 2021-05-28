<template>
    <el-row>
        <el-col :span="4"></el-col>
        <el-col :span="14">
            <div class="title">
                搜索: {{$attrs.title}}
            </div>
            <!--eslint-disable-->
            <div class="header">
                搜到相关博客 {{loading? '?': page.total}} 篇 <span v-if="loading"><i class="el-icon-loading"></i></span>
                <div class="res">


                    <template v-if="!loading && posts.length">
                        <simple-post-li :item="post" v-for="post in posts"></simple-post-li>
                        <div class="block">
                            <el-pagination size="30px"
                                           layout="prev, pager, next"
                                           :total="page.pages"
                                           @current-change="change">
                            </el-pagination>
                        </div>
                    </template>
                    <template v-else-if="!loading && !posts.length">
                        <el-empty description="没有有关文章"></el-empty>
                    </template>
                </div>
            </div>
        </el-col>
        <el-col :span="6">
            <all-tags></all-tags>
        </el-col>
    </el-row>
</template>

<script>
    import AllTags from "./allTags";
    import {Page} from "../../global/clazz";
    import axios from 'axios'
    import SimplePostLi from "./SimplePostLi";

    export default {
        name: "SearchResult",
        components: {SimplePostLi, AllTags},
        mounted() {
            this.getPosts2(this.$attrs.title, 1);
        },
        data() {
            return {
                page: new Page(),
                loading: true,
                message: '',
                posts: []
            }
        },
        methods: {
            getPosts2(title, paging) {
                axios.get(`/post/title/${title}`, {
                    params: {
                        page: paging,
                    }
                }).then(
                    data => {
                        if (data.data.errno === 0) {
                            return data.data;
                        } else {
                            return Promise.reject(data.data.message);
                        }
                    }, error => Promise.reject(error.toString())
                ).then(
                    data => {
                        console.log(data);
                        this.page = data.extend;
                        this.posts = data.extend.list;
                        this.loading = false;
                    }, error => {
                        this.message = error;
                        this.loading = false;
                    }
                )
            },
            change(cur) {
                this.getPosts2(this.$attrs.title, cur);
            }
        }
    }
</script>

<style scoped>
    .header {
        margin-top: 8px;
        margin-bottom: 20px;
    }

    .title {
        font-weight: bold;
        font-size: 40px;
        margin-top: 20px;
    }

    li {
        margin-left: 21px;
        margin-right: 21px;
        height: 110px;
        padding: 10px;
        border-bottom: 1px solid #e7e7e7;
        background-color: #fff;
    }

    .block {
        display: flex;
        justify-content: center;
        vertical-align: center;
        margin-bottom: 10px;
        margin-top: 10px;
    }
    .res{
        margin-top: 10px;
    }
</style>