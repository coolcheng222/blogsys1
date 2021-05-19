<template>
    <el-menu default-active="1-4-1" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose" :collapse="isCollapse">
        <el-menu-item index="2">
            <i class="el-icon-menu"></i>
        </el-menu-item>
        <el-menu-item index="3" disabled>
            <i class="el-icon-document"></i>
        </el-menu-item>
        <el-menu-item index="4">
            <i class="el-icon-setting"></i>
        </el-menu-item>
    </el-menu>
</template>

<script>
    export default {
        name: "sidebar"
    }
</script>

<style scoped>

</style>