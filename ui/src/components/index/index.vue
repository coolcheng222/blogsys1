<template>
    <el-backtop></el-backtop>
    <index-header></index-header>
    <router-view></router-view>
</template>

<script>
    // import 'bootstrap/dist/css/bootstrap.min.css'
    // import 'bootstrap/dist/js/bootstrap.min.js'
    import IndexHeader from "./indexHeader";
    export default {
        name: "index",
        components: {IndexHeader},
        methods:{

        }
        // eslint-disable-next-line no-unused-vars

    }
</script>

<style scoped>

</style>