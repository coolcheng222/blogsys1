<template>
    <article class="article" v-if="loaded">
        <h1 class="title">{{post.title}}</h1>
        <div class="info">
            <div class="tags">
                <span>标签:</span>
                <template v-if="post.tags.length === 0">
                    <span>无</span>
                </template>
                <template v-else>
                    <!--eslint-disable-->
                    &nbsp;<el-tag @click="$router.push(`/tag/${tag.tag}`)" effect="plain" size="mini" v-for="tag in post.tags">{{tag.tagname}}</el-tag>
                </template>
            </div>
            <div class="tags">
                <span class="star">
                    <i @click="toggle" class="el-icon-star-off" v-if="!faved"></i>
                    <i @click="toggle" v-else class="el-icon-star-on" style="color: #a0a000"></i>
                    收藏:{{post.faved + favnum}}
                </span>&nbsp;
                <span class="author" @click="$router.push(`/user/${post.author.uid}`)">作者: {{post.author.username}}</span>&nbsp;
                <span class="time">发布时间: {{getTime(post.time)}}</span>
            </div>


        </div>
        <div v-html="post.content" class="content"></div>
    </article>
    <article class="article" v-else>
        <el-skeleton :rows="5" animated/>
    </article>
</template>

<script>
    import axios from "axios";
    import {dateFormat} from "../../../global/clazz";
    import qs from 'qs';

    export default {
        name: "SinglePost",
        // eslint-disable-next-line vue/no-unused-components
        props: ['post', 'loaded'],
        inject: ['pid'],
        mounted() {
            this.check();
        },
        data() {
            return {
                faved: false,
                favnum: 0
            }
        },
        methods: {
            check() {
                axios.get("/fav/check", {
                    params: {
                        uid: this.$store.state.user.uid,
                        pid: this.pid
                    }
                }).then(
                    data => {
                        if (data.data === 0) {
                            return data.data;
                        } else {
                            return Promise.reject(data.message);
                        }
                    }, error => {
                        return Promise.reject(error.toString());
                    }).then(
                    data => {
                        this.fav = data.extend.res;
                    },
                    () => {
                        this.fav = false;
                    }
                )
            },
            getTime(date) {
                return dateFormat("YYYY-mm-dd HH:MM", date);
            },
            async toggle() {
                if (!(this.$store.state.user.isLogin)) {
                    this.$store.dispatch('toLogin',this.$route.fullPath);
                    this.$router.push("/login");
                }
                if (this.faved) {
                    this.favnum -= 1;
                    axios.delete(
                        `/fav?uid=${this.$store.state.user.uid}&pid=${this.pid}`
                    ).then(data => {
                        if (data.data.errno == 0) {
                            this.check();
                            // eslint-disable-next-line vue/no-mutating-props

                        } else {
                            return Promise.reject(data.message);
                        }
                    }, error => Promise.reject(error.toString())).catch(
                        ()=>{this.favnum += 1;}
                )
                } else{
            this.favnum += 1;
            axios.post(
                '/fav',
                qs.stringify({uid: this.$store.state.user.uid, pid: this.pid})
            ).then(
                data => {
                    if (data.data.errno == 0) {
                        this.check();

                    } else {
                        return Promise.reject(data.message);
                    }
                }, error => Promise.reject(error.toString())).catch(
                ()=>{this.favnum -= 1}
    )
    }
    this.faved = !this.faved;
    }
    }
    }
</script>

<style scoped>
    .article {
        margin: 20px 40px 20px 0;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        background-color: #fff4f4;
        padding-top: 40px;
        padding-left: 30px;
        padding-right: 80px;
        word-break: break-all;
        word-wrap: break-word;
        padding-bottom: 20px;
    }

    .title {
        color: #727272;
        font-size: 28px;
        margin-bottom: 30px;
    }

    .content {
        font-size: 1.5rem;
    }

    .info {
        background-color: #ffeded;
        width: 80%;
        padding-bottom: 10px;
        margin-bottom: 30px;
    }

    .tags {
        padding-left: 10px;
        padding-top: 10px;
    }

    .star {
        font-size: 17px;
    }
    .el-icon-star-off,.el-icon-star-off{
        cursor: pointer;
    }
    .author:hover{
        cursor: pointer;
        text-decoration: underline;
    }
</style>