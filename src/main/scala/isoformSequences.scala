package com.bio4j.data.uniprot

trait AnyIsoformSequence extends Any {

  def ID: String
  def sequence: String
}
