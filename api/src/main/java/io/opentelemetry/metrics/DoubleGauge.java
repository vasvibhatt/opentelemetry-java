/*
 * Copyright 2019, OpenTelemetry Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opentelemetry.metrics;

import io.opentelemetry.metrics.DoubleGauge.BoundDoubleGauge;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Gauge metric, to report instantaneous measurement of a double value. Gauges can go both up and
 * down. The gauges values can be negative.
 *
 * <p>Example:
 *
 * <pre>{@code
 * class YourClass {
 *
 *   private static final Meter meter = OpenTelemetry.getMeterRegistry().get("my_library_name");
 *   private static final DoubleGauge gauge =
 *       meter
 *           .gaugeDoubleBuilder("processed_jobs")
 *           .setDescription("Processed jobs")
 *           .setUnit("1")
 *           .setLabelKeys(Collections.singletonList("Key"))
 *           .build();
 *   // It is recommended to keep a reference to a Bound Instrument.
 *   private static final BoundDoubleGauge someWorkBound =
 *       gauge.getBound(Collections.singletonList("SomeWork"));
 *
 *   void doSomeWork() {
 *      // Your code here.
 *      someWorkBound.set(15);
 *   }
 *
 * }
 * }</pre>
 *
 * @since 0.1.0
 */
@ThreadSafe
public interface DoubleGauge extends Gauge<BoundDoubleGauge> {

  /**
   * Sets the given value.
   *
   * <p>The value added is associated with the current {@code Context} and provided LabelSet.
   *
   * @param val the new value.
   * @param labelSet the labels to be associated to this recording
   * @since 0.1.0
   */
  void set(double val, LabelSet labelSet);

  @Override
  BoundDoubleGauge bind(LabelSet labelSet);

  @Override
  void unbind(BoundDoubleGauge boundInstrument);

  /**
   * A {@code Bound Instrument} for a {@code DoubleGauge}.
   *
   * @since 0.1.0
   */
  @ThreadSafe
  interface BoundDoubleGauge {

    /**
     * Sets the given value.
     *
     * <p>The value added is associated with the current {@code Context}.
     *
     * @param val the new value.
     * @since 0.1.0
     */
    void set(double val);
  }

  /** Builder class for {@link LongGauge}. */
  interface Builder extends Gauge.Builder<Builder, DoubleGauge> {}
}
