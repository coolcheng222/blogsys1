<template>
    <router-view :class="appp"></router-view>
</template>

<script>
    export default {
        name: 'App',
        components: {},
        mounted() {
        },
        methods: {
            send(){
            }
        }
    }
</script>

<style>
    *{
        margin: 0;
        padding: 0;
    }
    html,body,#app {
        height: 100%;
        /*text-align: center;*/
        /*color: #2c3e50;*/
        /*margin-top: 60px;*/
    }
</style>
