<script   setup>
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { searchServerForStudent } from "@/api/course.js"
import { ElMessage, ElMessageBox } from 'element-plus';
import { addStudentCourseServer } from "@/api/studentCourse"
const tableData = ref([
    {
        no: '20160503',
        name: 'english',
        times: '189',
        teacher: 'tom',
        des: 'gaokao'
    },
    {
        no: '20160503',
        name: 'english',
        times: '189',
        teacher: 'tom',
        des: 'gaokao'
    },
    {
        no: '20160503',
        name: 'english',
        times: '189',
        teacher: 'tom',
        des: 'gaokao'
    }
])
const currentPage = ref(1)
const pageSize = ref(3)
const background = ref(false)
const disabled = ref(false)
const allNumber = ref(100)
const handleSizeChange = async (number) => {
    console.log(" items per page", number)
    searchBody.value.pageSize = number;
    await search()
}
const handleCurrentChange = async (number) => {
    console.log("current page: ", number)
    searchBody.value.pageNum = number
    await search()
}
// 搜索体
const searchBody = ref({
    searchName: "",
    teacherName: "",
    courseNo: "",
    pageNum: 1,
    pageSize: 3
})


//-------------------定义函数--------------------------
const search = async () => {
    const result = await searchServerForStudent(searchBody);
    tableData.value = result.data.items;
    allNumber.value = result.data.total;
}
search();

const searchButton = () => {
    searchBody.value.pageNum = 1;
    searchBody.value.pageSize = 3;
    search()
}
const reset = () => {
    searchBody.value.searchName = "";
    searchBody.value.teacherName = "";
    searchBody.value.courseNo = "";
}

const chooseCourse = async (row) => {
    console.log("row!!==", row);
    ElMessageBox.confirm(
        '你确认要选定该课程吗?',
        '温馨提示',
        {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: "warning",
        })
        .then(async () => {
            await addStudentCourseServer(row);
            ElMessage.success('选课成功！')
            //重新加载数据
            await search();
        })
        .catch(() => {
            ElMessage.info('用户取消了选定')
        })
}
</script>

<template>
    <div>
        <div style="margin-bottom: 20px;">
            <el-input v-model="searchBody.searchName" style="width: 220px" placeholder="课程名" :prefix-icon="Search" />
            <el-input v-model="searchBody.courseNo" style="width: 220px;margin-left:10px" placeholder="课程编号"
                :prefix-icon="Search" />
            <el-input v-model="searchBody.teacherName" style="width: 220px;margin-left:10px" placeholder="任课老师"
                :prefix-icon="Search" />
            <el-button type="primary" style="margin-left: 10px;" @click="searchButton">搜索</el-button>
            <el-button type="info" @click="reset">重置</el-button>
        </div>

        <div>
            <el-table :data="tableData" stripe style="width: 100%">
                <el-table-column prop="no" label="课程编号" />
                <el-table-column prop="name" label="课程名称" />
                <el-table-column prop="times" label="课时" />
                <el-table-column prop="teacherName" label="任课老师" />
                <el-table-column prop="des" label="课程描述" />
                <el-table-column fixed="right" label="操作" min-width="120">
                    <template #default="{ row }">
                        <!-- v-if="row.studentChoose==nul意思是如果这个课备选生没选择过，就渲染成这样 -->
                        <el-button v-if="row.studentChoose == null" type="primary" size="small" @click="chooseCourse(row)">
                            选定课程
                        </el-button>
                        <!-- 否则就这样 -->
                        <el-button v-else type="info" disabled="disable" size="small">已被选定</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div>
            <!--分页插件-->
            <el-pagination style="float: right;
      margin-top: 20px" v-model:current-page="currentPage" v-model:page-size="pageSize" :page-sizes="[3, 5, 10]"
                :disabled="disabled" :background="background" layout="prev, pager, next, jumper,total, sizes"
                :total="allNumber" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
    </div>
</template>

<style scoped></style>

