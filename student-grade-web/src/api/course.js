import request from "@/utils/request.js";

export const searchServer= async (searchBody)=>{
    return await request.get("/course/list",{
        params:{
            pageNum:searchBody.value.pageNum,
            pageSize:searchBody.value.pageSize,
            searchName:searchBody.value.searchName,
            courseNo:searchBody.value.courseNo,
            teacherName:searchBody.value.teacherName
        }
     })
}

export const searchServerForStudent= async (searchBody)=>{
    return await request.get("/course/listForStudent",{
        params:{
            pageNum:searchBody.value.pageNum,
            pageSize:searchBody.value.pageSize,
            searchName:searchBody.value.searchName,
            courseNo:searchBody.value.courseNo,
            teacherName:searchBody.value.teacherName
        }
    })
}

export const  addServer=async (courseModel)=>{
    await request.post("/course",courseModel.value);
}
export const updateServer=async (courseModel)=>{
    await request.put("/course",courseModel.value)
}
export const deleteServer=async (id)=>{
    await request.delete("/course?id="+id);
}