/*
 * unichain-core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * unichain-core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.unichain.core.capsule;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.unichain.core.Constant;
import org.unichain.protos.Contract.CreateSmartContract;
import org.unichain.protos.Contract.TriggerSmartContract;
import org.unichain.protos.Protocol.SmartContract;
import org.unichain.protos.Protocol.SmartContract.ABI;
import org.unichain.protos.Protocol.Transaction;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Slf4j(topic = "capsule")
public class ContractCapsule implements ProtoCapsule<SmartContract> {

  private SmartContract smartContract;

  /**
   * constructor TransactionCapsule.
   */
  public ContractCapsule(SmartContract smartContract) {
    this.smartContract = smartContract;
  }

  public ContractCapsule(byte[] data) {
    try {
      this.smartContract = SmartContract.parseFrom(data);
    } catch (InvalidProtocolBufferException e) {
      // logger.debug(e.getMessage());
    }
  }

  public static CreateSmartContract getSmartContractFromTransaction(Transaction unx) {
    try {
      Any any = unx.getRawData().getContract(0).getParameter();
      CreateSmartContract createSmartContract = any.unpack(CreateSmartContract.class);
      return createSmartContract;
    } catch (InvalidProtocolBufferException e) {
      return null;
    }
  }

  public static TriggerSmartContract getTriggerContractFromTransaction(Transaction unx) {
    try {
      Any any = unx.getRawData().getContract(0).getParameter();
      TriggerSmartContract contractTriggerContract = any.unpack(TriggerSmartContract.class);
      return contractTriggerContract;
    } catch (InvalidProtocolBufferException e) {
      return null;
    }
  }

  public byte[] getCodeHash() {
    return this.smartContract.getCodeHash().toByteArray();
  }

  public void setCodeHash(byte[] codeHash) {
    this.smartContract = this.smartContract.toBuilder().setCodeHash(ByteString.copyFrom(codeHash))
        .build();
  }

  @Override
  public byte[] getData() {
    return this.smartContract.toByteArray();
  }

  @Override
  public SmartContract getInstance() {
    return this.smartContract;
  }

  @Override
  public String toString() {
    return this.smartContract.toString();
  }

  public byte[] getOriginAddress() {
    return this.smartContract.getOriginAddress().toByteArray();
  }

  public long getConsumeUserResourcePercent() {
    long percent = this.smartContract.getConsumeUserResourcePercent();
    return max(0, min(percent, Constant.ONE_HUNDRED));
  }

  public long getOriginEnergyLimit() {
    long originEnergyLimit = this.smartContract.getOriginEnergyLimit();
    if (originEnergyLimit == Constant.PB_DEFAULT_ENERGY_LIMIT) {
      originEnergyLimit = Constant.CREATOR_DEFAULT_ENERGY_LIMIT;
    }
    return originEnergyLimit;
  }

  public void clearABI() {
    this.smartContract = this.smartContract.toBuilder().setAbi(ABI.getDefaultInstance()).build();
  }

  public byte[] getUnxHash() {
    return this.smartContract.getUnxHash().toByteArray();
  }
}
