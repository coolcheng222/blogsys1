<template>
    <el-row>
        <el-col :span="4"></el-col>
        <el-col :span="16">
            <div class="account">
                <div class="item">修改密码</div>
                <div class="item">修改邮箱</div>
            </div>
        </el-col>
        <el-col :span="4"></el-col>
    </el-row>
</template>

<script>
    export default {
        name: "Account"
    }
</script>

<style scoped>
    .account{
        margin: 10px auto;
        width: 100%;
    }
</style>