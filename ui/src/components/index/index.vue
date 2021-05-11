<template>
    <index-header></index-header>
    <router-view></router-view>
</template>

<script>

    import IndexHeader from "./indexHeader";
    export default {
        name: "index",
        components: {IndexHeader},
        methods:{

        }
    }
</script>

<style scoped>

</style>