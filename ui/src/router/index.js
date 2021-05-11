import {createRouter, createWebHistory} from 'vue-router'
import User from "../components/user/User";
import Register from "../components/user/Register";
import Login from "../components/user/Login";
import index from "../components/index/index"
import Test1 from "../components/test/Test1";
import indexBody1 from "../components/index/indexBody1";
import NotFound from "../components/NotFound";

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
    {
        name:'index',
        path:'/index',
        component: index,
        alias: '/',
        children: [{
            name: 'defaultIndex',
            path:'default',
            component: indexBody1,
            alias: ''
        }]
    },
    {name:'test',path:"/test",component: Test1},
    {
        name: 'notFound',
        path: '/:path(.*)*',
        component: NotFound
    }
]


const router = createRouter({
    history: createWebHistory("/blogui"),
    routes
})



export default router
