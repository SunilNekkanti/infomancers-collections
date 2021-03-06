package com.infomancers.tests.enhancers;

import com.infomancers.collections.yield.asm.NewMember;
import com.infomancers.collections.yield.asm.TypeDescriptor;
import com.infomancers.collections.yield.asmbase.YielderInformationContainer;
import com.infomancers.collections.yield.asmtree.InsnEnhancer;
import com.infomancers.collections.yield.asmtree.enhancers.LoadEnhancer;
import com.infomancers.tests.TestYIC;
import org.junit.Test;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;


/**
 * Copyright (c) 2009, Aviad Ben Dov
 * <p/>
 * All rights reserved.
 * <p/>
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * <p/>
 * 1. Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 * 3. Neither the name of Infomancers, Ltd. nor the names of its contributors may be
 * used to endorse or promote products derived from this software without specific
 * prior written permission.
 * <p/>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public class LoadEnhancerTests extends EnhancerTestsBase {

    @Test
    public void iload2() {
        YielderInformationContainer info = new TestYIC(1,
                new NewMember(1, TypeDescriptor.Integer),
                new NewMember(2, TypeDescriptor.Integer));

        final VarInsnNode insn = new VarInsnNode(Opcodes.ILOAD, 2);
        InsnList original = createList(insn);


        InsnList expected = createList(
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, "slot$2", "I")
        );

        InsnEnhancer enhancer = new LoadEnhancer();

        enhancer.enhance(owner, original, null, info, insn);

        compareLists(expected, original);
    }

    @Test
    public void iload_memberIsObject() {
        YielderInformationContainer info = new TestYIC(1,
                new NewMember(1, TypeDescriptor.Object));

        final VarInsnNode insn = new VarInsnNode(Opcodes.ILOAD, 1);
        InsnList original = createList(
                insn
        );

        NewMember slot = info.getSlot(1);

        InsnList expected = createList(
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, slot.getName(), slot.getDesc()),
                new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Integer"),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I")
        );

        InsnEnhancer enhancer = new LoadEnhancer();

        enhancer.enhance(owner, original, null, info, insn);

        compareLists(expected, original);
    }

    @Test
    public void lload_memberIsObject() {
        YielderInformationContainer info = new TestYIC(1,
                new NewMember(1, TypeDescriptor.Object));

        final VarInsnNode insn = new VarInsnNode(Opcodes.LLOAD, 1);
        InsnList original = createList(
                insn
        );

        NewMember slot = info.getSlot(1);

        InsnList expected = createList(
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, slot.getName(), slot.getDesc()),
                new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Long"),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J")
        );

        InsnEnhancer enhancer = new LoadEnhancer();

        enhancer.enhance(owner, original, null, info, insn);

        compareLists(expected, original);
    }

    @Test
    public void dload_memberIsObject() {
        YielderInformationContainer info = new TestYIC(1,
                new NewMember(1, TypeDescriptor.Object));

        final VarInsnNode insn = new VarInsnNode(Opcodes.DLOAD, 1);
        InsnList original = createList(
                insn
        );

        NewMember slot = info.getSlot(1);

        InsnList expected = createList(
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, slot.getName(), slot.getDesc()),
                new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Double"),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D")
        );

        InsnEnhancer enhancer = new LoadEnhancer();

        enhancer.enhance(owner, original, null, info, insn);

        compareLists(expected, original);
    }

    @Test
    public void fload_memberIsObject() {
        YielderInformationContainer info = new TestYIC(1,
                new NewMember(1, TypeDescriptor.Object));

        final VarInsnNode insn = new VarInsnNode(Opcodes.FLOAD, 1);
        InsnList actual = createList(
                insn
        );

        NewMember slot = info.getSlot(1);

        InsnList expected = createList(
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, slot.getName(), slot.getDesc()),
                new TypeInsnNode(Opcodes.CHECKCAST, "java/lang/Float"),
                new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F")
        );

        InsnEnhancer enhancer = new LoadEnhancer();

        enhancer.enhance(owner, actual, null, info, insn);

        compareLists(expected, actual);
    }

    @Test
    public void twoAloads_membersAreInt() {
        YielderInformationContainer info = new TestYIC(1, new NewMember(1, TypeDescriptor.Integer),
                new NewMember(2, TypeDescriptor.Integer));

        final VarInsnNode load1 = new VarInsnNode(Opcodes.ILOAD, 1);
        final VarInsnNode load2 = new VarInsnNode(Opcodes.ILOAD, 2);
        final InsnList actual = createList(load1, load2);

        NewMember slot1 = info.getSlot(1);
        NewMember slot2 = info.getSlot(2);

        InsnList expected = createList(
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, slot1.getName(), slot1.getDesc()),
                new VarInsnNode(Opcodes.ALOAD, 0),
                new FieldInsnNode(Opcodes.GETFIELD, owner.name, slot2.getName(), slot2.getDesc())
        );

        InsnEnhancer enhancer = new LoadEnhancer();

        enhancer.enhance(owner, actual, null, info, load1);
        enhancer.enhance(owner, actual, null, info, load2);

        compareLists(expected, actual);


    }

}
