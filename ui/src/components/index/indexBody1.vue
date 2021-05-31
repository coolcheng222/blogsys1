<template>
    <el-row>
        <el-col :span="1"></el-col>
        <el-col :span="17">
            <el-carousel indicator-position="outside">
                <el-carousel-item v-for="item in 4" :key="item">
<!--                    <img src="@/assets/Login.jpg">-->
                    <div class="dec"></div>
                </el-carousel-item>
            </el-carousel>
            <el-tabs>
                <el-tab-pane label="最新文章" name="first"></el-tab-pane>
            </el-tabs>
            <blog-list></blog-list>
        </el-col>
        <el-col :span="6">
            <all-reply></all-reply>
        </el-col>
    </el-row>
</template>

<script>
    import BlogList from "./blogall/blogList";
    import {mapState} from "vuex";
    import AllReply from "./replyall/allReply";
    export default {
        name: "indexBody1",
        components: { AllReply, BlogList},
        computed:{
            // eslint-disable-next-line no-undef
            ...mapState({
                'isLogin': state=>state.user.isLogin
            })
        },
        // eslint-disable-next-line no-unused-vars
        beforeRouteEnter(){
        },
        // eslint-disable-next-line no-unused-vars
        beforeRouteLeave (to, from) {
        }
    }
</script>

<style scoped>
    .el-row{
        background-color: #F6F6F6;
    }
    .el-carousel >>> .el-carousel__item{
        width: 100%;
        background-image: url("~@/assets/simple1.jpg");
        background-size: contain;
    }
    .el-carousel,.el-tabs{
        margin-left: 21px;
        margin-right: 21px;
        margin-top: 10px;
    }
    .el-tabs >>> .el-tabs__item{
        font-weight: bold;
        font-size: 30px;
    }
</style>