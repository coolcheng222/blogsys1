<template>
    <div class="pop">
        <el-header>
            <div class="role">{{rolestate[userinfo.rolename]}}</div>
            <div class="user">{{userinfo.username}}</div>
        </el-header>
        <el-main>
            <el-row type="flex" class="row-bg" justify="space-around">
                <el-col :span="6">
                    <div class="grid-content bg-purple-light"><label>关注:</label><br><b>{{userinfo.subing}}</b></div>
                </el-col>
                <el-col :span="6">
                    <div class="grid-content bg-purple"><label>粉丝:</label><br><b>{{userinfo.subbed}}</b></div>
                </el-col>
                <el-col :span="6">
                    <div class="grid-content bg-purple"><label>文章:</label><br><b>{{userinfo.posts}}</b></div>
                </el-col>
            </el-row>
            <div class="items"><i class="el-icon-user"></i> 个人中心</div>
            <div class="items"><i class="el-icon-chat-round"></i> 未读消息
                <div class="unread">{{unread}}</div>
            </div>
            <div class="items" @click="toWrite"><i class="el-icon-edit"></i> 创作文章</div>
            <div class="items" @click="logout"><i class="el-icon-position"></i> 登&nbsp;&nbsp;&nbsp;&nbsp;出</div>

        </el-main>
    </div>

</template>
<el-dialog
        title="提示"
        v-model="dialogVisible"
        width="30%"
        :before-close="handleClose">
    <span>登出成功</span>
    <template #footer>
    <span class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
    </template>
</el-dialog>
<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import {UserInfo} from "../../../global/clazz";

    export default {
        name: "UserInfo1",
        data() {
            return {
                userinfo: new UserInfo(),
                rolestate: {
                    admin: '管理员',
                    baned: '被封禁',
                    banned: '被封禁',
                    user: '用户'
                },
                dialogVisible: false,
                unread: 0
            }
        },
        mounted() {
            axios.get(`userinfo/${this.uid}`, {}).then(data => {
                    if (data.data.errno === 0) {
                        return data.data;
                    } else {
                        return Promise.reject(data.data);
                    }
                }, error => {
                    return Promise.reject(error.toString());
                }
            ).then(
                data => {
                    Object.assign(this.userinfo, data.extend);
                }, error => {
                    console.log(error);
                }
            );
            axios.get(`unread/count/${this.uid}`).then(data => {
                    if (data.data.errno === 0) {
                        return data.data;
                    } else {
                        return Promise.reject(data.data);
                    }
                }, error => {
                    return Promise.reject(error.toString());
                }
            ).then(
                data => {
                    this.unread = data.extend.count;
                }, error => {
                    console.log(error);
                }
            )

        },
        computed: {
            ...
                mapState({
                    'uid': store => store.user.uid
                })
        },
        methods: {
            toWrite() {
                this.$router.push({name: 'write'});
            },
            logout() {
                this.$store.dispatch('clear')
                this.$store.dispatch('logout');
                this.dialogVisible = true;
                // window.location.reload();
                // alert("登出成功");
            },handleClose(){
                this.dialogVisible = false;
            }
        }
    }
</script>

<style scoped>
    .role, .user {
        text-align: center;

    }

    .role {
        font-size: 12px;
    }

    .user {
        font-weight: bold;
        font-size: 20px;
    }

    .grid-content {
        text-align: center;
        font-size: 14px;
        cursor: pointer;
    }

    .el-row {
        border-top: 1px solid gainsboro;
        border-bottom: 1px solid gainsboro;
    }

    .el-header, .el-main {
        padding-right: 0;
        padding-left: 0;
    }

    .el-header {
        padding-top: 10px;
    }

    .el-row {
        padding-top: 10px;
        padding-bottom: 10px;
    }

    .items:hover {
        background-color: #e5d7d7;
        cursor: pointer;
    }

    .items {
        height: 50px;
        line-height: 50px;
        padding-left: 23px;
        padding-right: 23px;
        font-size: 15px;
        border-bottom: 1px solid #d8ffcf;
    }

    .unread {
        float: right;
        height: 26px;
        margin-top: 12px;
        margin-bottom: 12px;
        width: 26px;
        border-radius: 15px;
        background-color: #fff;
        text-align: center;
        line-height: 26px;
    }
</style>