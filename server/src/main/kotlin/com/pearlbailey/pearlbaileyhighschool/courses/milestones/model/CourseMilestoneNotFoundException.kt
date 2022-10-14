package com.pearlbailey.pearlbaileyhighschool.courses.milestones.model

import com.pearlbailey.pearlbaileyhighschool.common.model.exception.NotFoundException

class CourseMilestoneNotFoundException(id: Int) : NotFoundException("Course Milestone with id $id not found.")
