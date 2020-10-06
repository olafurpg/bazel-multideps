package multideps.loggers

import java.util.concurrent.atomic.AtomicInteger

class ProgressBarState() {
  private val BeforeStart = 0
  private val Active = 1
  private val AfterStop = 2
  private val state = new AtomicInteger(BeforeStart)
  def tryStart(): Boolean = state.compareAndSet(BeforeStart, Active)
  def tryStop(): Boolean = state.compareAndSet(Active, AfterStop)
  def stop(): Unit = state.set(AfterStop)
  def isBeforeStart(): Boolean = state.get() == BeforeStart
  def isActive(): Boolean = state.get() == Active
  def isAfterStop(): Boolean = state.get() == AfterStop
}
