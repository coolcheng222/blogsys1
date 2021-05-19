<template>

        <div class="userinfo">
            <div class="header"><i class="el-icon-data-analysis"></i> 精彩评论</div>
            <el-main>
                <ul>
                    <template v-if="!loaded">
                        <!--eslint-disable-next-line-->
                        <li v-for="i in [1,2,3,4,5]">
                        </li>
                    </template>
                    <template v-else>
                        <!--eslint-disable-next-line-->
                        <li v-for="(reply,index) in replyList">
                            <reply-li :item="reply" :index="index"></reply-li>
                        </li>
                    </template>

                </ul>
            </el-main>
        </div>
    <!--        <el-footer>c</el-footer>-->
</template>

<script>
    import axios from "axios";
    import ReplyLi from "./replyLi";

    export default {
        name: "allReply",
        components: {ReplyLi},
        data() {
            return {
                replyList: [],
                loaded: false
            }
        },
        mounted() {
            axios.get("reply/all").then(
                data => {
                    if (data.data.errno === 1) {
                        return Promise.reject(data.data.message);
                    } else {
                        // console.log("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
                        this.loaded = true;
                        return data.data;
                    }
                },
                error=>Promise.reject(error.toString())
            ).then(
                data => {
                    this.replyList = data.extend.list;
                },
                msg => {
                    console.log(msg);
                }
            )
        }

    }
</script>

<style scoped>
    .userinfo {
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        margin-right: 20px;
        margin-top: 10px;
        background-color: #fff;
    }

    .header {
        margin-top: 10px;
        margin-left: 10px;
        line-height: 30px;
        height: 30px;
    }

    li {
        /*text-decoration: underline;*/
        /*width: 400px;*/
        width: 255px;
        border-top: 1px solid #e5d7d7;
        list-style-type: none;
    }

    .userinfo {
        position: fixed;
    }
</style>