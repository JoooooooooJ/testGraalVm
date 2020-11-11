package br.com.gubee.gubeegraalvmspring.domain.product.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(@field:Id var id: String,
                   var title: String)