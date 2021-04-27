import {createRouter, createWebHistory} from 'vue-router'
import User from "../components/user/User";
import Register from "../components/user/Register";
import Login from "../components/user/Login";
import index from "../components/index/index"
import Test1 from "../components/test/Test1";

const routes = [
    {
        path: '/user',
        name: 'user',
        component: User,
        children: [
            {
                path: 'register',
                name: 'register',
                component: Register,
                alias: '/register'
            },
            {
                path: 'login',
                name: 'login',
                component: Login,
                alias: '/login'
            }
        ]
    },
    {name:'index',path:'/index',component: index},
    {name:'default',path:"/",redirect:'/index'},
    {name:'test',path:"/test",component: Test1}
]


const router = createRouter({
    history: createWebHistory("/blogui"),
    routes
})



export default router
