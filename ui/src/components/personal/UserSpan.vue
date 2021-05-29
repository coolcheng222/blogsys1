<template>
    <div class="tabs">
        <el-tabs v-model="activeName">
            <el-tab-pane :label="'文章 ' + info.posts" name="myarticle"></el-tab-pane>
            <el-tab-pane v-if="mySelf" label="关注动态" name="subscribe"></el-tab-pane>
            <el-tab-pane v-if="mySelf" label="浏览历史" name="history"></el-tab-pane>
            <el-tab-pane v-if="mySelf" label="评论" name="myreply"></el-tab-pane>
            <el-tab-pane v-if="mySelf" label="收藏" name="faving"></el-tab-pane>
            <el-tab-pane label="关注/粉丝" name="subing"></el-tab-pane>
            <el-tab-pane v-if="mySelf" label="账户设置" name="account"></el-tab-pane>

        </el-tabs>
    </div>
    <component :is="comName" :uid="info.uid"></component>


</template>

<script>
    import MyArticle from "./tabs/MyArticle";
    import Subscribe from './tabs/Subscribe';
    import reply from './tabs/reply';
    import Faving from './tabs/Faving'
    import Subing from './tabs/Subing'
    import Account from './tabs/Account'
    import History from './tabs/History'
    export default {
        name: "UserSpan",
        components: {MyArticle,Subscribe,reply,Faving,Subing,Account,History},
        props:['info'],
        compnents:{
            MyArticle
        },
        mounted(){
            if(this.$route.query.tab){
                this.activeName = this.$route.query.tab;
            }
        },
        data(){
            return {
                maping:{
                    myarticle: 'MyArticle',
                    subscribe: 'Subscribe',
                    myreply: 'reply',
                    faving: 'Faving',
                    subing: 'Subing',
                    account: 'Account',
                    history: 'History'
                },
                activeName: 'myarticle'
            }
        },
        computed:{
            comName(){
                return this.maping[this.activeName];
            },
            mySelf(){
                return this.info.uid === this.$store.state.user.uid
            }
        }
    }
</script>

<style scoped>
    .tabs{
        background-color: #fff;
        height: 50px;
        padding-left: 10px;
        margin-top: 0;
    }
    >>> .el-tabs__item{
        height: 50px;
        line-height: 50px;
    }
</style>