import request from "@/utils/request.js";

export const addStudentCourseServer = async (row) => {
  await request.post("/studentCourse/add/" + row.no);
};

export const getStudentCourseListPersonalServer = async (searchBody) => {
  const result = await request.get(
    "/studentCourse/listPersonal",
    {
      params: {
        courseNo: searchBody.value.courseNo,
        pageNum:searchBody.value.pageNum,
        pageSize:searchBody.value.pageSize
      },
    }
  );
  return result;
};

export const delCourseChooseServer=async (row)=>{
  await request.delete("/studentCourse/"+row.courseNo);
}

export const getStudentCourseListServer=async(searchBody)=>{
  const result= await request.get("/studentCourse/list",
    {
    params:{
      courseNo:searchBody.value.courseNo,
      studentName:searchBody.value.studentName,
      pageNum:searchBody.value.pageNum,
      pageSize:searchBody.value.pageSize
    }
  })
  return result
}

export const delCourseChooseServerByManager=async (row)=>{
  await request.delete("/studentCourse/"+row.courseNo+"/"+row.studentId);
}

export const accessServer=async(gradeBody)=>{
  await request.put("/studentCourse/access",gradeBody.value)
}

export const feedbackServer=async (gradeBody)=>{
  await request.put("/studentCourse/feedback",gradeBody.value)
}