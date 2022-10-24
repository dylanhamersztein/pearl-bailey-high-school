package com.pearlbailey.coursemilestonemanager.api.model

import com.pearlbailey.commontools.exception.NotFoundException

class CourseMilestoneNotFoundException(id: Int) : NotFoundException("Course Milestone with id $id not found.")
