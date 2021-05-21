<template>
    <div v-if="isLogin">
        <div class="inputing">
            <el-row>
                <el-col :span="1"></el-col>
                <el-col :span="16">
                    <el-input type="textarea" v-model="reply.content" placeholder="在这里发评论"></el-input>
                </el-col>
                <el-col :span="1"></el-col>
                <el-col :span="2" v-if="!pubing">
                    <el-button @click="postReply" :disabled="!loaded">发表</el-button>
                </el-col>
                <el-col :span="2" v-else>
                    <el-button disabled>发布中<i class="el-icon-loading"></i></el-button>
                </el-col>
            </el-row>

            <el-dialog
                    v-model="dialogVisible"
                    width="30%"
                    >
                <span>回复不能为空</span>
                    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
            </el-dialog>

        </div>
    </div>
    <div v-else>
        <div class="prompt">游客,想要评论请先<span @click="toLogin" class="login-prop">登录</span></div>
    </div>
</template>

<script>
    import {mapState} from "vuex";
    import {Reply} from "../../global/clazz";
    import axios from 'axios'

    export default {
        name: "writereply",
        props: ['post', 'loaded'],
        data() {
            return {
                reply: new Reply(),
                pubing: false,
                dialogVisible: false
            }
        },
        computed: {
            ...mapState({
                'isLogin': state => state.user.isLogin,
                'uid': state => state.user.uid
            })
        },
        methods: {
            toLogin() {
                this.$store.dispatch('toLogin', this.$route.fullPath);
                this.$router.push('/login');
            },
            postReply() {
                if (!this.reply.content || this.reply.content === "") {
                    this.dialogVisible = true;
                    return;
                }
                this.reply.user = {};
                this.reply.post = this.post;
                this.reply.user.uid = this.uid;
                this.pubing = true;
                axios.post(`/reply`, this.reply).then(
                    () => {
                        this.pubing = false;
                        this.$router.go(0);
                    }, () => {
                        this.pubing = false;
                    }
                );
            }
        },
    }
</script>

<style scoped>
    .prompt {
        margin-left: 10px;
        margin-right: 40px;
        height: 68px;
        margin: 0 40px 0 0px;
        text-align: center;
        line-height: 68px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
        border-radius: 2px;
        background-color: #fff;
    }

    .login-prop {
        color: cornflowerblue;
        text-decoration: underline;
        cursor: pointer;
    }

    .inputing {
        margin-right: 50px;
        margin-left: 10px;
        height: 78px;
        margin-bottom: 20px;
    }

    .inputing >>> .el-textarea__inner {
        resize: none;
        height: 78px;
    }

    .el-button {
        float: right;
        margin-top: 10px;
        margin-right: 20px;
    }
    >>> .el-dialog__body{
        height: 100px;
    }
</style>