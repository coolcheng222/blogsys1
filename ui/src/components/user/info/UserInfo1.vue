<template>
    <el-header>
        <div >{{rolestate[userinfo.rolename]}}</div>
        <div >{{userinfo.username}}</div>
    </el-header>
    <el-main>
        <div>{{unread}}</div>
        <div>{{userinfo.subbed}}</div>
        <div>{{userinfo.subing}}</div>
        <div>{{userinfo.posts}}</div>
    </el-main>
</template>

<script>
    import axios from "axios";
    import {mapState} from "vuex";
    import {UserInfo} from "../../../global/clazz";

    export default {
        name: "UserInfo1",
        data() {
            return {
                userinfo: new UserInfo(),
                rolestate:{
                    admin: '管理员',
                    baned: '被封禁',
                    banned: '被封禁',
                    user: '用户'
                },
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
                    return error.toString();
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
                    return error.toString();
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
        }
    }
</script>

<style scoped>

</style>