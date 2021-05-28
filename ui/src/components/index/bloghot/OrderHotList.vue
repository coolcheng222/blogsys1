<template>
    <!--eslint-disable-->
    <ul class="infinit-list2">
        <template v-if="loading">


            <!--eslint-disable-next-line-->
            <li class="infinit-list2-item" v-for="i in [1,2,3,4,5]">
                <el-skeleton :rows="1" :animated="true"/>
            </li>

        </template>
        <template v-else>
            <!--eslint-disable-next-line-->
            <li class="infinit-list2-item" v-for="item in list">
                <blog-li2 :item="item"></blog-li2>
            </li>

        </template>
    </ul>
</template>

<script>
    import {Page} from "../../../global/clazz";
    import axios from "axios";
    import BlogLi2 from './blogLi2'

    export default {
        name: "OrderHotList",
        components: {BlogLi2},
        props: ['orderClause'],
        data() {
            return {
                loading: true,
                time: 0,
                asc: false,
                pageInfo: new Page(),
                list: [],
                colors: ['#abacad','#000','red','blue'],
                index: 0
            }
        },
        mounted() {
            this.time = new Date().getTime();
            this.getPage(1);
        },
        methods: {
            getPage(page) {
                this.loading = true;
                if (!page) {
                    page = 1;
                }
                axios.get(`/post`, {
                    params: {
                        asc: this.asc,
                        before: this.time,
                        page,
                        clause: this.orderClause
                    }
                }).then(
                    data => {
                        console.log(data.data);
                        if (data.data.errno === 0) {
                            return data.data;
                        } else {
                            return Promise.reject(data.data.message);
                        }
                    }, error => {
                        return Promise.reject(error.toString());
                    }
                ).then(
                    data => {
                        console.log(data);
                        this.list = data.extend.list;
                        this.page = data.extend;
                    }
                ).finally(
                    () => {
                        this.loading = false;
                    }
                )
            }
        },
        computed: {
            title() {
                if (this.orderClause === 'reply') {
                    return '回复最多'
                } else if (this.orderClause === 'faved') {
                    return '收藏最多'
                }
                return null;
            }
        }
    }
</script>

<style scoped>
    h1{
        margin-left: 20px;
    }
    .infinit-list2 {
        margin-left: 21px;
        margin-right: 21px;
    }

    .infinit-list2 .infinit-list2-item {
        height: 65px;
        padding: 10px;
        width: 100%;
        border-radius: 3px;
        border-bottom: 1px solid #E7E7E7;
        background-color: #fff;
        text-decoration: none;
        list-style: none;
    }

    .el-footer span {
        font-size: 16px;
        color: #99aaaa;
    }

    .prompt {
        height: 50px;
        text-align: center;
        margin-bottom: 10px;
        line-height: 50px;
    }
</style>